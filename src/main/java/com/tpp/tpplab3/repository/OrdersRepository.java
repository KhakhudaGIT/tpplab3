package com.tpp.tpplab3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpp.tpplab3.models.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}
