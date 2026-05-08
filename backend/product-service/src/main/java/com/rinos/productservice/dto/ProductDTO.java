package com.rinos.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Product name is required")
    private String name;
    private String description;
    @Positive(message = "Product price must be greater then zero")
    private Double price;
    private String brand;
    private Integer categoryId;
    private String status;
}
