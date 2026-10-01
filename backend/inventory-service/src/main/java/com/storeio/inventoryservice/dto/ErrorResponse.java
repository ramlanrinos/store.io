package com.storeio.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;
    
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    
    private Map<String, String> errors;
}
