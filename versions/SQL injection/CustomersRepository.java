package com.tpp.tpplab3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpp.tpplab3.models.Customers;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Integer> {
}