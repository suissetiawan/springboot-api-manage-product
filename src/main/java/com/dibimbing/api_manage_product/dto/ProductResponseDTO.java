package com.dibimbing.api_manage_product.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private int status;
    private String message;
    private Object data;
    
}
