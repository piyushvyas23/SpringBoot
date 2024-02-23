package com.cdac.training.productrest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.training.productrest.exception.ResourceNotFoundException;
import com.cdac.training.productrest.model.Product;
import com.cdac.training.productrest.service.ProductService;

/*Spring RestController annotation is used to create RESTful web services using Spring MVC. 
 * Spring RestController takes care of mapping request data to the defined request handler method. 
 * Once response body is generated from the handler method, it converts it to JSON or XML response. 
 * @RestController indicates that this class handles HTTP requests and automatically 
 * serializes the results to JSON.
 * @RequestMapping - maps HTTP request with a path to a controller 
 * */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value= "/api")
public class ProductController {

	
	@GetMapping("/hello")
	public String sayHello(){
		return "Hello World from Spring Boot REST API";
	}
	
	@Autowired
	private ProductService pservice;
	
	/* ResponseEntity represents an HTTP response, including headers, body, and status.
	 *  @RequestBody annotation automatically deserializes the JSON into a Java type
	 *  */
	
	//Open PostMan, make a POST Request - http://localhost:8087/product-restapi/api/products/
    //Select body -> raw -> JSON 
    //Insert JSON product object.
	/*
	 * 
	 * {
    "name":"HDD",
    "brand":"Seagate",
    "madein":"Uk",
    "price":8000
	}
	 * */
	@PostMapping("/products")
	public ResponseEntity<Product> saveProduct(@Validated @RequestBody Product product) {
		try {
				Product p=pservice.saveProduct(product);
				return ResponseEntity.status(HttpStatus.CREATED).body(p);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	// Postman/Browser --> Controller -->Service -> Repository -> DataBase
		// All layers will use Model when required
		//Open PostMan, make a GET Request - http://localhost:8087/product-restapi/api/products/
		@GetMapping("/products")
		public ResponseEntity<List<Product>> getAllProducts(){
			try {
				List<Product> products= pservice.listAll();
				return ResponseEntity.ok(products);
			}
			catch(Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		}
		

		/* @PathVariable is a Spring annotation which indicates that a method parameter should be
	     *  bound to a URI template variable.
	       @PathVariable annotation is used to read an URL template variable.
	     */
		//Open PostMan, make a GET Request - http://localhost:8087/product-restapi/api/products/4
		
		@GetMapping("/products/{id}")
		public ResponseEntity<Product> getProductById(@PathVariable(value="id") Long pId)
					throws ResourceNotFoundException {
				Product p=pservice.getSingleProduct(pId).
						orElseThrow(() -> new ResourceNotFoundException("Product Not Found for this Id :"+pId));
				
				return ResponseEntity.ok().body(p);
		}
		
		//Open PostMan, make a PUT Request - http://localhost:8087/product-restapi/api/products/3
	    //Select body -> raw -> JSON 
	    //Update JSON product object with new Values.
		
		@PutMapping("/products/{id}")
		public ResponseEntity<Product> updateProduct(@PathVariable(value="id") Long pId,
				@Validated @RequestBody Product p) throws ResourceNotFoundException {
			
			Product product=pservice.getSingleProduct(pId).
					orElseThrow(() -> new ResourceNotFoundException("Product Not Found for this Id :"+pId));
			//Update product with new values
			product.setBrand(p.getBrand());
			product.setMadein(p.getMadein());
			product.setName(p.getName());
			product.setPrice(p.getPrice());
			
			final Product updatedProduct=pservice.saveProduct(product);
			return ResponseEntity.ok().body(updatedProduct);
	}
		
		//Open PostMan, make a DELETE Request - http://localhost:8087/product-restapi/api/products/3
		@DeleteMapping("/products/{id}")
		public ResponseEntity<Map<String,Boolean>> deleteProduct(@PathVariable(value="id") Long pId)
				throws ResourceNotFoundException{
			
			pservice.getSingleProduct(pId).
			orElseThrow(() -> new ResourceNotFoundException("Product Not found for thid Id :"+pId));
			
			pservice.deleteProduct(pId);
			
			Map<String,Boolean> response=new HashMap<String, Boolean>();
			response.put("Deleted", Boolean.TRUE);
			return ResponseEntity.ok(response);
		}
		
		//Open PostMan, make a GET Request - http://localhost:8087/product-restapi/api/products/search?name=pendrive
		@GetMapping("/products/search")
	    public ResponseEntity<?> searchProductsByName(@RequestParam("name") String name) {
	        try {
	            List<Product> products = pservice.searchProductsByName(name);
	            
	            if (products.isEmpty()) {
	                return new ResponseEntity<>("No products found with the given name.", HttpStatus.NOT_FOUND);
	            }
	            
	            return new ResponseEntity<>(products, HttpStatus.OK);
	        } catch (Exception ex) {
	        	//database error
	            return new ResponseEntity<>("An error occurred while searching for products.", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
}
