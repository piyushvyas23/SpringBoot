package com.cdac.training.productrest.model;

import jakarta.persistence.*;

/**
 * 
 * @author rajgs
 * 
 * Spring Boot JPA with Hibernate
 * 
 * Spring Boot JPA is a Java specification for managing relational data in Java applications. 
 * It allows us to access and persist data between Java object/ class and relational database. 
 * JPA follows Object-Relation Mapping (ORM). It is a set of interfaces. 
 * It also provides a runtime EntityManager API for processing queries and transactions on the objects 
 * against the database. 
 * It uses a platform-independent object-oriented query language JPQL (Java Persistent Query Language).
 * JPA is implemented with Annotations
 */

/* The @Entity annotation specifies that the class is an entity and is mapped to a database table.*/
@Entity
public class Product {
	
	/*The @Id annotation specifies the primary key of an entity and the @GeneratedValue provides for 
	 * the specification of generation strategies for the values of primary keys. */
	// @SequenceGenerator(name="product_seq", initialValue = 1000, allocationSize = 1) 
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)//, generator = "product_seq")
	 private Long pid;

     private String name;
     private String brand;
     @Column(nullable = false)
     private String madein;
     private float price;
	public Product() {
		// TODO Auto-generated constructor stub
	}
	public Product(Long pid, String name, String brand, String madein, float price) {
		super();
		this.pid = pid;
		this.name = name;
		this.brand = brand;
		this.madein = madein;
		this.price = price;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getMadein() {
		return madein;
	}
	public void setMadein(String madein) {
		this.madein = madein;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
     
     
}
