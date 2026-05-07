package com.rinos.productservice.repository;

import com.rinos.productservice.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {
}
