package com.dibimbing.api_manage_product.utils;

import com.dibimbing.api_manage_product.dto.*;
import org.springframework.http.HttpStatus;

public class GenerateResponse {

    public static ProductResponseDTO success(HttpStatus status, String message, Object data) {
        ProductResponseDTO response = new ProductResponseDTO();
        response.setStatus(status.value());
        response.setMessage(message);
        if (data != null) {
            response.setData(data);
        }

        return response;
    }
}
