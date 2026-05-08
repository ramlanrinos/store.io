package com.rinos.productservice.service;

import com.rinos.productservice.dto.ProductDTO;
import com.rinos.productservice.entity.Product;
import com.rinos.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    public ProductDTO createProduct(ProductDTO productDTO) {
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

        return convertToDto(savedProduct);
    }

    public ProductDTO updateProduct(long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).get();

        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getBrand() != null) {
            product.setBrand(productDTO.getBrand());
        }
        if (productDTO.getCategoryId() != null) {
            product.setCategoryId(productDTO.getCategoryId());
        }
        if (productDTO.getStatus() != null) {
            product.setStatus(productDTO.getStatus());
        }

        product.setUpdatedAt(new Date());
        Product updatedProduct = productRepository.save(product);

        return convertToDto(updatedProduct);
    }

    private ProductDTO convertToDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setBrand(product.getBrand());
        productDTO.setCategoryId(product.getCategoryId());
        productDTO.setStatus(product.getStatus());
        return productDTO;
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
