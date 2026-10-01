package com.storeio.productservice.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequest {
    private Long categoryId;
    private String name;
    private String description;
    
    @PositiveOrZero(message = "Price must be non-negative")
    private BigDecimal price;
    
    private Boolean active;
}
