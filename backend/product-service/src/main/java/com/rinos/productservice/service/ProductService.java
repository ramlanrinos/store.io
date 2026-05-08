package com.rinos.productservice.service;

import com.rinos.productservice.dto.ProductCreateDTO;
import com.rinos.productservice.entity.Product;
import com.rinos.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    public ProductService (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).get();
    }

    public ProductCreateDTO createProduct(ProductCreateDTO productDTO) {
        // Convert DTO to Entity
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setBrand(productDTO.getBrand());
        product.setCategoryId(productDTO.getCategoryId());
        product.setStatus(productDTO.getStatus());

        // Save product to the database
        Product savedProduct = productRepository.save(product);

        return convertToCreateDto(savedProduct);
    }

    private ProductCreateDTO convertToCreateDto(Product product) {
        ProductCreateDTO productDTO = new ProductCreateDTO();
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setBrand(product.getBrand());
        productDTO.setCategoryId(product.getCategoryId());
        productDTO.setStatus(product.getStatus());
        return productDTO;
    }

    public Product updateProduct(long id, Product product) {
        Product oldProduct = productRepository.findById(id).get();
        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setBrand(product.getBrand());
        oldProduct.setStatus(product.getStatus());
        oldProduct.setUpdatedAt(product.getUpdatedAt());
        return productRepository.save(oldProduct);
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
