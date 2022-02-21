package com.minibanking.customer.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minibanking.customer.api.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	void save(Optional<Customer> customer);

}
