// // //v1
// // package com.example.demo.controller;

// // import java.util.List;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.web.bind.annotation.DeleteMapping;
// // import org.springframework.web.bind.annotation.GetMapping;
// // import org.springframework.web.bind.annotation.PathVariable;
// // import org.springframework.web.bind.annotation.PostMapping;
// // import org.springframework.web.bind.annotation.PutMapping;
// // import org.springframework.web.bind.annotation.RequestBody;
// // import org.springframework.web.bind.annotation.RequestMapping;
// // import org.springframework.web.bind.annotation.RestController;

// // import com.example.demo.model.Product;
// // import com.example.demo.service.ProductService;

// // @RestController
// // @RequestMapping("/api/products")
// // public class ProductController {

// //     @Autowired
// //     private ProductService productService;

// //     @GetMapping
// //     public List<Product> getAllProducts() {
// //         return productService.getAllProducts();
// //     }

// //     @GetMapping("/{id}")
// //     public Product getProductById(@PathVariable Integer id) {
// //         return productService.getProductById(id);
// //     }

// //     @PostMapping
// //     public Product createProduct(@RequestBody Product product) {
// //         return productService.createProduct(product);
// //     }

// //     @PutMapping("/{id}")
// //     public Product updateProduct(@PathVariable Integer id, @RequestBody Product product) {
// //         return productService.updateProduct(id, product);
// //     }

// //     @DeleteMapping("/{id}")
// //     public void deleteProduct(@PathVariable Integer id) {
// //         productService.deleteProduct(id);
// //     }

// // }



// package com.example.demo.controller;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.demo.model.Product;
// import com.example.demo.service.ProductService;

// @RestController
// @RequestMapping("/products")
// public class ProductController {

//     @Autowired
//     private ProductService productService;

//     @GetMapping
//     public List<Product> getAllProducts() {
//         return productService.findAll();
//     }

//     @GetMapping("/{id}")
//     public Optional<Product> getProductById(@PathVariable Integer id) {
//         return productService.findById(id);
//     }

//     @PostMapping
//     public Product createProduct(@RequestBody Product product) {
//         return productService.save(product);
//     }

//     @PutMapping("/{id}")
//     public Product updateProduct(@PathVariable Integer id, @RequestBody Product product) {
//         product.setId(id);
//         return productService.save(product);
//     }

//     @DeleteMapping("/{id}")
//     public void deleteProduct(@PathVariable Integer id) {
//         productService.deleteById(id);
//     }
// }


// / v2
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
//             @RequestParam(defaultValue = "10") int size) {
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

//     @PostMapping
//     public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//         try {
//             Product createdProduct = productStorage.insertProduct(product);
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
//             return ResponseEntity.ok(updatedProduct);
//         } catch (RuntimeException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @DeleteMapping("/{code}")
//     public ResponseEntity<Void> deleteProduct(@PathVariable String code) {
//         try {
//             productStorage.deleteProductByCode(code);
//             return ResponseEntity.noContent().build();
//         } catch (RuntimeException e) {
//             return ResponseEntity.notFound().build();
//         }
//     }
// }

//v3
package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.memory.ProductStorage;
import com.example.demo.model.Product;



@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductStorage productStorage;

    @GetMapping
    public ResponseEntity<List<Product>> listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<Product> products = productStorage.getStorage().values().stream()
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Product> getProduct(@PathVariable String code) {
        Product product = productStorage.findProductByCode(code);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }


    // @PostMapping("/create")
    // public ResponseEntity<Product> createProduct(@RequestBody Product product) {
    //     try {
    //         Product createdProduct = productStorage.insertProduct(product);
    //         productStorage.saveDataset();  // Ensure data is saved after creating a product
    //         return ResponseEntity.ok(createdProduct);
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.badRequest().body(null);
    //     }
    // }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productStorage.insertProduct(product);
            productStorage.saveDataset();  // Ensure data is saved after creating a product
            return ResponseEntity.ok(createdProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{code}")
    public ResponseEntity<Product> updateProduct(@PathVariable String code, @RequestBody Product product) {
        product.setCode(code);
        try {
            Product updatedProduct = productStorage.updateProduct(product);
            productStorage.saveDataset();  // Ensure data is saved after updating a product
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // @PutMapping("/{code}")
    // public ResponseEntity<Product> updateProductByCode(@PathVariable String code, @RequestBody Product product) {
    //     Product existingProduct = productStorage.findProductByCode(code);
    //     if (existingProduct == null) {
    //         return ResponseEntity.notFound().build();
    //     }
        
    //     // Update the existing product with the new details
    //     existingProduct.setName(product.getName());
    //     existingProduct.setDescription(product.getDescription());
    //     // Update other fields as needed
        
    //     // Save the updated product
    //     try {
    //         Product updatedProduct = productStorage.updateProduct(existingProduct);
    //         productStorage.saveDataset();  // Ensure data is saved after updating a product
    //         return ResponseEntity.ok(updatedProduct);
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.badRequest().body(null);
    //     }
    // }



    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String code) {
        try {
            productStorage.deleteProductByCode(code);
            productStorage.saveDataset();  // Ensure data is saved after deleting a product
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
