package com.cdac.training.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdac.training.inventory.model.Product;

// Defining JPA repository for Product Class to perform DB Operations
// Long - Data type of Id Field
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	/*
	 * It consists of predefined methods for Database operations. (CRUD)
	 * save(), deleteById(),findById(),findAll()
	 */
}
