package com.dibimbing.api_manage_product.utils.exceptions.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO<T> {
    private int status;
    private String error;
    private T message;
}
