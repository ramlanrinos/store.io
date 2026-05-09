package com.rinos.productservice.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
    @NotBlank(message = "Category name is required")
    private String name;
    private String description;
    private String status;
}
