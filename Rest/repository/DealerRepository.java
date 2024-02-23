package com.cdac.training.productrest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdac.training.productrest.model.Dealer;
import com.cdac.training.productrest.model.DealerAndAddressProjection;

public interface DealerRepository extends JpaRepository<Dealer, Long> {

	//Custom Method to fetch record/object based on email field - non id field.
	public Optional<Dealer> findByEmail(String email);
	
	/*
	 * *
 * @Query Annotation is used for defining custom queries in Spring Data JPA. 
 * When you are unable to use the query methods to execute database operations 
 * then you can use @Query to write a more flexible query to fetch data. 
 *  
	- @Query Annotation supports both JPQL and native SQL queries.
	- It also supports SpEL expressions.
	- @Param in method arguments to bind query parameter.
	- To define Multiple Joins
 */
// Custom Queries	
@Query("SELECT new com.cdac.training.productrest.model.DealerAndAddressProjection"
	+ "(d.id, d.fname,d.lname,d.phoneNo,"
	+ "d.email, a.street, a.city,a.pincode) "
	+ "FROM Dealer d JOIN d.address a")
List<DealerAndAddressProjection> findSelectedFieldsFromDealerAndAddress();
}
