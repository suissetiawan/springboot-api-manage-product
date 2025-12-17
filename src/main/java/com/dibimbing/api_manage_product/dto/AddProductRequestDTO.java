package com.dibimbing.api_manage_product.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequestDTO {

    private String name;
    private Double price;
    private String description;
    private Integer stock;

}
