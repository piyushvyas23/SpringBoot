package com.cdac.training.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cdac.training.inventory.model.Product;
import com.cdac.training.inventory.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService pService;
	
	@GetMapping("/newProduct")
	public String showProductAddForm(Model model) {
		Product product =new Product();
		model.addAttribute("product",product);
		return "addProduct"; // returns view + Product Object
	}
	
	@PostMapping("/save")
	public String saveProduct(@ModelAttribute("product") Product product) {
		pService.saveProduct(product); // Invoke service method
	   
	    return "redirect:products";
	}
	
	@GetMapping("/products")
	public String viewProductsPage(Model model) {
	    List<Product> listProducts = pService.listAll();
	    model.addAttribute("listProducts", listProducts);
	     
	    return "products"; // view + Product List
	}
	
	@GetMapping("/edit")
	public ModelAndView showEditProductPage(@RequestParam("id") int id) {
	    ModelAndView mav = new ModelAndView("edit_product");
	    Product product = pService.getProductById(id);
	    mav.addObject("product", product);
	    return mav;
	}
	
	@GetMapping("/delete")
	public String deleteProduct(@RequestParam("id") int id) {
	    pService.deleteProductById(id);
	    return "redirect:products";       
	}

}
