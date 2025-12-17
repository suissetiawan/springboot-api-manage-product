package com.dibimbing.api_manage_product.services;

import com.dibimbing.api_manage_product.dto.*;
import com.dibimbing.api_manage_product.utils.*;
import com.dibimbing.api_manage_product.entities.Product;
import com.dibimbing.api_manage_product.utils.exceptions.custom.*;
import com.dibimbing.api_manage_product.repositories.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final GeneralValidation generalValidation;

    public ResponseEntity<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAllByIsDeletedFalse();
        ProductResponseDTO res = GenerateResponse.success(HttpStatus.OK, "Get all products successfully", products);
        return ResponseEntity.ok(res);
    }

    public ResponseEntity<ProductResponseDTO> addProduct(AddProductRequestDTO reqData) {
        generalValidation.validateProduct(
                reqData.getName(),
                reqData.getPrice(),
                reqData.getStock());

        if (productRepository.findByNameAndIsDeletedFalse(reqData.getName()).isPresent()) {
            throw new ValidationException("Product name already exists");
        }

        Product product = new Product();
        product.setName(reqData.getName());
        product.setPrice(reqData.getPrice());
        product.setDescription(reqData.getDescription());
        product.setStock(reqData.getStock());

        productRepository.save(product);

        ProductResponseDTO res = GenerateResponse.success(HttpStatus.CREATED, "Product added successfully", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    public ResponseEntity<ProductResponseDTO> updateProduct(Long id, UpdateProductRequestDTO reqData) {
        generalValidation.validateProduct(
                reqData.getName(),
                reqData.getPrice(),
                reqData.getStock()
        );

        if (productRepository.findByNameAndIsDeletedFalse(reqData.getName()).isPresent()) {
            throw new ValidationException("Product name already exists");
        }

        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setName(reqData.getName());
        product.setPrice(reqData.getPrice());
        product.setDescription(reqData.getDescription());
        product.setStock(reqData.getStock());

        productRepository.save(product);
        ProductResponseDTO res = GenerateResponse.success(HttpStatus.OK, "Product updated successfully", product);
        return ResponseEntity.ok(res);
    }

    public ResponseEntity<ProductResponseDTO> updateStock(Long id, UpdateStockRequestDTO reqData) {

        if (reqData.getSellStock() <= 0) {
            throw new ValidationException("Sell stock must be greater than 0");
        }

        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Integer newStock = product.getStock() - reqData.getSellStock();

        generalValidation.validateStock(newStock);

        product.setStock(newStock);
        productRepository.save(product);

        ProductResponseDTO res = GenerateResponse.success(HttpStatus.OK, "Stock updated successfully", product);
        return ResponseEntity.ok(res);
    }

    public ResponseEntity<?> deleteProduct(Long id) {
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setIsDeleted(true);
        productRepository.save(product);

        return ResponseEntity.noContent().build();
    }
}
