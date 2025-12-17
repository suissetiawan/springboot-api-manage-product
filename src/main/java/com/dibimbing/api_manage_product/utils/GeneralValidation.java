package com.dibimbing.api_manage_product.utils;

import com.dibimbing.api_manage_product.utils.exceptions.custom.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class GeneralValidation {

    public void validateProduct(String name, Double price, Integer stock) {

        if (name == null || name.isEmpty()) {
            throw new ValidationException("Name is required");
        }

        if (price == null) {
            throw new ValidationException("Price is required");
        }

        if (price <= 0) {
            throw new ValidationException("Price must be greater than 0");
        }

        if (stock == null) {
            throw new ValidationException("Stock is required");
        }

        if (stock < 0) {
            throw new ValidationException("Stock must be greater than or equal to 0");
        }
    }

    public void validateStock(Integer sellStock) {
        if (sellStock == null) {
            throw new ValidationException("Sell stock is required");
        }

        if (sellStock < 0) {
            throw new ValidationException("stock not enough");
        }
    }
}
