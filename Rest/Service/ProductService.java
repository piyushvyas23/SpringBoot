package com.cdac.training.productrest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.training.productrest.model.Product;
import com.cdac.training.productrest.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
	
	@Autowired
	private ProductRepository prepo;
	
	public Product saveProduct(Product p) {
		return prepo.save(p);   // Invokes save() method predefined in JPA repo
	}
	
	 public List<Product> listAll(){
 		
 		return prepo.findAll(); //Defined in JPA repo.
 	}
	 // Optional handles nullpointer Exceptions - Java 8 feature
	public Optional<Product> getSingleProduct(long id){
		return prepo.findById(id); // Defined in JPA repo.
	}
	
	public void deleteProduct(long id) {
		 prepo.deleteById(id); // Defined in JPA repo.
	}
	
	public List<Product> searchProductsByName(String name) {
        return prepo.findProductsByNameContainingIgnoreCase(name);
    }

}
