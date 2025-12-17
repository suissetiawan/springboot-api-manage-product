package com.dibimbing.api_manage_product.utils.exceptions.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    private int status;
    private String message;
    private String path;
}
