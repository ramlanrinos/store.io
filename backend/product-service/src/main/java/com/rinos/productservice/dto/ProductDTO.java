package com.rinos.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String description;
    private double price;
    private String brand;
    private int categoryId;
    private String status;
}
