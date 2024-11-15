package com.tpp.tpplab3.controllers;

import com.tpp.tpplab3.models.Customers;
import com.tpp.tpplab3.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

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
    
}
