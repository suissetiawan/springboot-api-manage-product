package com.dibimbing.api_manage_product.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UpdateProductRequestDTO {
    
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;
    
    private String description;
    
    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private Integer stock;

}
