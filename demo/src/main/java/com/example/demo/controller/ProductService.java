package com.example.demo.controller;

import com.example.demo.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();
    private int nextId = 1;

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(Integer id) {
        Optional<Product> optionalProduct = products.stream().filter(p -> p.getId().equals(id)).findFirst();
        return optionalProduct.orElse(null);
    }

    public Product createProduct(Product product) {
        product.setId(nextId++);
        products.add(product);
        return product;
    }

    public Product updateProduct(Integer id, Product product) {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            // Update properties of existing product
            existingProduct.setCode(product.getCode());
            existingProduct.setName(product.getName());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setType(product.getType());
            existingProduct.setDescription(product.getDescription());
        }
        return existingProduct;
    }

    public void deleteProduct(Integer id) {
        products.removeIf(p -> p.getId().equals(id));
    }
}
