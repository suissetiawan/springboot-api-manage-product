package com.dibimbing.api_manage_product.dto;

import lombok.Data;

@Data
public class UpdateProductRequestDTO {
    private String name;
    private Double price;
    private String description;
    private Integer stock;

}
