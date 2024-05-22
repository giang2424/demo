
package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.memory.ProductStorage;
import com.example.demo.model.Product;

@RestController
@RequestMapping("api/products")
public class ProductStorageController {

    @Autowired
    private ProductStorage productStorage;

    @GetMapping("/{code}")
    public Product getProductByCode(@PathVariable String code) {
        return productStorage.findProductByCode(code);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productStorage.insertProduct(product);
    }

    @PutMapping("/{code}")
    public Product updateProduct(@PathVariable String code, @RequestBody Product product) {
        product.setCode(code);
        return productStorage.updateProduct(product);
    }

    @DeleteMapping("/{code}")
    public Product deleteProduct(@PathVariable String code) {
        return productStorage.deleteProductByCode(code);
    }

    @GetMapping
    public Map<String, Product> getAllProducts() {
        return productStorage.getStorage();
    }
}