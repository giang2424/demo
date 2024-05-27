// package com.example.demo.controller;

// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.demo.memory.ProductStorage;
// import com.example.demo.model.Product;



// @RestController
// @RequestMapping("/api/products")
// public class ProductController {

//     @Autowired
//     private ProductStorage productStorage;

//     @GetMapping
//     public ResponseEntity<List<Product>> listProducts(
//             @RequestParam(defaultValue = "0") int page,
//             @RequestParam(defaultValue = "5") int size) {
//         List<Product> products = productStorage.getStorage().values().stream()
//                 .skip(page * size)
//                 .limit(size)
//                 .collect(Collectors.toList());
//         return ResponseEntity.ok(products);
//     }

//     @GetMapping("/{code}")
//     public ResponseEntity<Product> getProduct(@PathVariable String code) {
//         Product product = productStorage.findProductByCode(code);
//         if (product == null) {
//             return ResponseEntity.notFound().build();
//         }
//         return ResponseEntity.ok(product);
//     }


//     // @PostMapping("/create")
//     // public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//     //     try {
//     //         Product createdProduct = productStorage.insertProduct(product);
//     //         productStorage.saveDataset();  // Ensure data is saved after creating a product
//     //         return ResponseEntity.ok(createdProduct);
//     //     } catch (RuntimeException e) {
//     //         return ResponseEntity.badRequest().body(null);
//     //     }
//     // }

//     @PostMapping
//     public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//         try {
//             Product createdProduct = productStorage.insertProduct(product);
//             // productStorage.saveDataset();  // Ensure data is saved after creating a product
//             return ResponseEntity.ok(createdProduct);
//         } catch (RuntimeException e) {
//             return ResponseEntity.badRequest().body(null);
//         }
//     }

//     @PutMapping("/{code}")
//     public ResponseEntity<Product> updateProduct(@PathVariable String code, @RequestBody Product product) {
//         product.setCode(code);
//         try {
//             Product updatedProduct = productStorage.updateProduct(product);
//             // productStorage.saveDataset();  // Ensure data is saved after updating a product
//             return ResponseEntity.ok(updatedProduct);
//         } catch (RuntimeException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     // @PutMapping("/{code}")
//     // public ResponseEntity<Product> updateProductByCode(@PathVariable String code, @RequestBody Product product) {
//     //     Product existingProduct = productStorage.findProductByCode(code);
//     //     if (existingProduct == null) {
//     //         return ResponseEntity.notFound().build();
//     //     }
        
//     //     // Update the existing product with the new details
//     //     existingProduct.setName(product.getName());
//     //     existingProduct.setDescription(product.getDescription());
//     //     // Update other fields as needed
        
//     //     // Save the updated product
//     //     try {
//     //         Product updatedProduct = productStorage.updateProduct(existingProduct);
//     //         productStorage.saveDataset();  // Ensure data is saved after updating a product
//     //         return ResponseEntity.ok(updatedProduct);
//     //     } catch (RuntimeException e) {
//     //         return ResponseEntity.badRequest().body(null);
//     //     }
//     // }



//     @DeleteMapping("/{code}")
//     public ResponseEntity<Void> deleteProduct(@PathVariable String code) {
//         try {
//             productStorage.deleteProductByCode(code);
//             // productStorage.saveDataset();  // Ensure data is saved after deleting a product
//             return ResponseEntity.noContent().build();
//         } catch (RuntimeException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }
// }

package com.example.demo.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.memory.ProductStorage;
import com.example.demo.model.Product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/products")
@Api(value = "Product Management System", description = "Operations pertaining to products in Product Management System")
public class ProductController {

    @Autowired
    private ProductStorage productStorage;
    
    @ApiOperation(value = "View a list of available products", response = List.class)
    @GetMapping
    public ResponseEntity<List<Product>> listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "code") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        List<Product> products = productStorage.getStorage().values().stream()
                .sorted(getComparator(sortBy, sortDir))
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    private Comparator<Product> getComparator(String sortBy, String sortDir) {
        Comparator<Product> comparator;

        switch (sortBy) {
            case "id":
                comparator = Comparator.comparing(Product::getId);
                break;
            case "code":
                comparator = Comparator.comparing(Product::getCode);
                break;
            case "name":
                comparator = Comparator.comparing(Product::getName);
                break;
            case "description":
                comparator = Comparator.comparing(Product::getDescription);
                break;
            case "brand":
                comparator = Comparator.comparing(Product::getBrand);
                break;
            default:
                comparator = Comparator.comparing(Product::getCode);
        }

        if ("desc".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    @ApiOperation(value = "Get a product by code")
    @GetMapping("/{code}")
    public ResponseEntity<Product> getProduct(@PathVariable String code) {
        Product product = productStorage.findProductByCode(code);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @ApiOperation(value = "Create a new product")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productStorage.insertProduct(product);
            productStorage.saveProducts();
            return ResponseEntity.ok(createdProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ApiOperation(value = "Update an existing product")
    @PutMapping("/{code}")
    public ResponseEntity<Product> updateProduct(@PathVariable String code, @RequestBody Product product) {
        product.setCode(code);
        try {
            Product updatedProduct = productStorage.updateProduct(product);
            productStorage.saveProducts();
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Delete a product by code")
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String code) {
        try {
            productStorage.deleteProductByCode(code);
            productStorage.saveProducts();
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
