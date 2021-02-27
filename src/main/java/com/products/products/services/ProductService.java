package com.products.products.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.products.products.models.entity.Product;
import com.products.products.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> list(){
        Iterable<Product> product = repository.findAll(PageRequest.of(0, 50, Sort.by(Sort.Direction.ASC, "id")));
        List<Product> list = new ArrayList<Product>();
        product.forEach(list::add);
        return list;
    }

    public Product create(Product product){
        return repository.save(product);
    }

    public Product save(Product product){
        return repository.save(product);
    }

    public Optional<Product> find(Long id){
        return repository.findById(id);
    }
}
