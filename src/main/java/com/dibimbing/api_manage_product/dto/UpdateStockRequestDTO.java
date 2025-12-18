package com.dibimbing.api_manage_product.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;

@Data
public class UpdateStockRequestDTO {
    @Min(value = 1, message = "Sell stock must be greater than 0")
    private Integer sellStock;

}
