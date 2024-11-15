package com.tpp.tpplab3.service;

import com.tpp.tpplab3.models.Customers;
import com.tpp.tpplab3.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomersRepository customerRepository;

    public List<Customers> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customers> findById(int id) {
        return customerRepository.findById(id);
    }

    public void addCustomer(Customers customer) {
        customerRepository.save(customer);
    }

    public void updateCustomer(Customers updatedCustomer) {
        Customers existingCustomer = customerRepository.findById(updatedCustomer.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setSurname(updatedCustomer.getSurname());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setEmail(updatedCustomer.getEmail());

        existingCustomer.setOrders(updatedCustomer.getOrders());

        customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }
}
