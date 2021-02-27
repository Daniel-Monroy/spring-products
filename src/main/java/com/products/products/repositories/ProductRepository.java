package com.products.products.repositories;

import com.products.products.models.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product, Long>{
     
    
}
