package com.cdac.training.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.training.inventory.model.Product;
import com.cdac.training.inventory.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
	
	@Autowired  //DI using Field
	private ProductRepository prepo;
	
	public void saveProduct(Product p) {
		prepo.save(p); // Save product Object to Database using save() method of JPA
	}
	
	public List<Product> listAll() {
        return prepo.findAll(); // defined in JPA repo
    }
	
	public Product getProductById(long id) {
        return prepo.findById(id).get();  // defined in JPA repo
    }
     
    public void deleteProductById(long id) {
        prepo.deleteById(id);  // defined in JPA repo
    }


}
