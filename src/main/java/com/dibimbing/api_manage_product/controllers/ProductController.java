package com.dibimbing.api_manage_product.controllers;

import org.springframework.web.bind.annotation.*;
import com.dibimbing.api_manage_product.services.ProductService;
import com.dibimbing.api_manage_product.dto.*;
import org.springframework.http.ResponseEntity;




@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody AddProductRequestDTO addProductRequestDTO) {
        return productService.addProduct(addProductRequestDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequestDTO updateProductRequestDTO) {
        return productService.updateProduct(id, updateProductRequestDTO);
    }

    @PatchMapping("/update-stock/{id}")
    public ResponseEntity<ProductResponseDTO> updateStock(@PathVariable Long id, @RequestBody UpdateStockRequestDTO updateStockRequestDTO) {
        return productService.updateStock(id, updateStockRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

}
