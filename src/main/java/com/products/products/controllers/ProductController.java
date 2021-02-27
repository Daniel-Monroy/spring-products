package com.products.products.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.products.products.models.entity.Product;
import com.products.products.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@CrossOrigin(origins =  "http://localhost:8081")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/get")
	public ResponseEntity<List<Product>> getAll(@RequestParam(required = false) Integer id) {
        try 
        {
			List<Product> products = productService.list();
			if (products.isEmpty()) 
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(products, HttpStatus.OK);
		} 
        catch (Exception e) 
        {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @PostMapping("/add")
	public  ResponseEntity<Object> create(@Valid @RequestBody Product product, BindingResult result) {
        if(result.hasErrors())
        {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
        }
		try {
            Product productCreated = productService.create(product);
			return new ResponseEntity<>(productCreated, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("updateStock/{id}")
	public ResponseEntity<Product> updateStock(@PathVariable Long id, @Valid @RequestBody Product product) {
		Optional<Product> _product = productService.find(id);
		if(_product.isPresent())
		{
			try {
				Product item = _product.get();
				item.setName(product.getName());
				item.setCode(product.getCode());
				item.setStock(product.getStock());
				return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
