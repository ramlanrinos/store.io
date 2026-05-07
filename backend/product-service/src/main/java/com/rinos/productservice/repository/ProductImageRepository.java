package com.rinos.productservice.repository;

import com.rinos.productservice.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImages, Long> {
}
