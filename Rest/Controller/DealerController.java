package com.cdac.training.productrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.training.productrest.exception.ResourceNotFoundException;
import com.cdac.training.productrest.model.Address;
import com.cdac.training.productrest.model.Dealer;
import com.cdac.training.productrest.model.DealerAndAddressProjection;
import com.cdac.training.productrest.service.DealerService;

/*

Spring MVC provides @CrossOrigin annotation that marks the annotated method or type as permitting cross-origin requests.
The CORS (Cross-Origin Resource Sharing) allows a webpage to request additional resources into the browser from other domains
such as API data using AJAX, font files, style sheets etc. 

*/
/*
 * Controller for Registration & Login process of a Dealer
 */
@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/api")
public class DealerController {
	
	@Autowired
	private DealerService dservice;
	

	/* 
	 * Request --> FC --> User Controllers ->Service -> Repos -> DB
	 */
	//Open PostMan, make a POST Request - http://localhost:8087/product-restapi/api/register
    //Select body -> raw -> JSON 
    //Insert JSON product object.
/*
 * {
    "email": "james@gmail.com",
    "fname": "James",
    "lname": "Gosling",
    "password": "Hello123",
    "dob": "2017-02-01",
    "phoneNo": "9247230222",
    "address": {
        "street": "Silicon Valley",
        "city": "California",
        "pincode": "11001"
    }
}
 */
	
	@PostMapping("/register")
	public ResponseEntity<String> createDealer(@Validated @RequestBody Dealer dealer) {
		try {
		Address address = dealer.getAddress();
		
		 // Establish the bi-directional relationship
        address.setDealer(dealer);
        dealer.setAddress(address);
				
		Dealer registeredDealer = dservice.registerDealer(dealer);
	        if (registeredDealer!= null) {
	            return ResponseEntity.ok("Registration successful");
	        } else {
	            return ResponseEntity.badRequest().body("Registration failed");
	        }
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An Error Ocurred: "+e.getMessage());
			
		}
	}
	
	//Open Postman and make POST request - http://localhost:8087/product-restapi/api/login
	/*
	 * {
    "email":"james@gmail.com",
    "password":"Hello12"
	}
	 */
			@PostMapping("/login")
			public ResponseEntity<Boolean> loginDealer(@Validated @RequestBody Dealer dealer) throws ResourceNotFoundException
			{
				Boolean isAuthenticated = false;
				String email=dealer.getEmail();
				String password=dealer.getPassword();
			
				Dealer d = dservice.loginDealer(email).orElseThrow(() ->
				new ResourceNotFoundException("Dealer not found for this email :: " + email));
				
				if(email.equals(d.getEmail()) && password.equals(d.getPassword()))
				{
					isAuthenticated = true;

				}
				return ResponseEntity.ok(isAuthenticated);
			}
			
			//Open Postman and make GET request - http://localhost:8087/product-restapi/api/dealers
			@GetMapping("/dealers")
		    public ResponseEntity<List<DealerAndAddressProjection>> getDealerInfo() {
				try {
		        List<DealerAndAddressProjection> selectedFields = dservice.getDealerInfo();
		        return ResponseEntity.ok(selectedFields);
				}
				catch(Exception e) {
					e.printStackTrace();
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body(null);
				}
		    }
}
