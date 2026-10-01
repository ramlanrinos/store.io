package com.storeio.productservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatchProductRequest {

    @NotEmpty(message = "Product IDs list cannot be empty")
    private List<Long> productIds;
}
