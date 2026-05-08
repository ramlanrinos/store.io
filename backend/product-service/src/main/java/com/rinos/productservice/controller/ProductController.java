package com.rinos.productservice.controller;

import com.rinos.productservice.dto.ProductDTO;
import com.rinos.productservice.entity.Product;
import com.rinos.productservice.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

//    @PutMapping("/{id}")
//    public Product updateProduct(@PathVariable long id, @RequestBody Product product) {
//        return productService.updateProduct(id, product);
//    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }
}
