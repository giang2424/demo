package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductByCode(String code) {
        return Optional.ofNullable(productRepository.findByCode(code));
    }

    public Product createProduct(Product productDetails) {
        Product product = new Product();
        product.setCode(productDetails.getCode());
        product.setName(productDetails.getName());
        product.setCategory(productDetails.getCategory());
        product.setBrand(productDetails.getBrand());
        product.setType(productDetails.getType());
        product.setDescription(productDetails.getDescription());
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(String code, Product productDetails) {
        return getProductByCode(code).map(product -> {
            product.setName(productDetails.getName());
            product.setCategory(productDetails.getCategory());
            product.setBrand(productDetails.getBrand());
            product.setType(productDetails.getType());
            product.setDescription(productDetails.getDescription());
            return productRepository.save(product);
        });
    }


    public boolean deleteProduct(String code) {
        return getProductByCode(code).map(product -> {
            productRepository.delete(product);
            return true;
        }).orElse(false);
    }
}