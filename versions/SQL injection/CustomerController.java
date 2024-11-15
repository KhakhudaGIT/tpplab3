package com.tpp.tpplab3.controllers;

import com.tpp.tpplab3.models.Customers;
import com.tpp.tpplab3.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    @GetMapping("/add")
    public String addCustomerForm(Model model) {
        model.addAttribute("customer", new Customers());
        return "add-customer";
    }

    @PostMapping("/add")
    public String addCustomer(@Valid @ModelAttribute("customer") Customers customer, BindingResult result) {
        if (result.hasErrors()) {
            return "add-customer";
        }
        customerService.addCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String editCustomerForm(@PathVariable("id") int id, Model model) {
        Customers customer = customerService.findById(id).orElse(null);
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "edit-customer";
        } else {
            return "redirect:/customers";
        }
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable("id") int id, @Valid @ModelAttribute("customer") Customers customer, BindingResult result) {
        if (result.hasErrors()) {
            return "edit-customer";
        }
        customer.setCustomerId(id);
        customerService.updateCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") int id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
    
    @PostMapping("/execute-query")
    public String executeQuery(@RequestParam("sqlQuery") String sqlQuery, Model model) {
        try {
            String trimmedQuery = sqlQuery.trim().toLowerCase();
            
            // Check if query is of type SELECT
            if (trimmedQuery.startsWith("select")) {
                List<Map<String, Object>> result = jdbcTemplate.queryForList(sqlQuery);
                model.addAttribute("queryResult", result);
            } else {
                // Execute update for INSERT, UPDATE, DELETE
                int rowsAffected = jdbcTemplate.update(sqlQuery);
                model.addAttribute("message", "Query executed successfully. Rows affected: " + rowsAffected);
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error executing query: " + e.getMessage());
        }
        return "customers";
    }
}
