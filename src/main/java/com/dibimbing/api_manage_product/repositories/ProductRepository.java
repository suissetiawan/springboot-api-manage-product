package com.dibimbing.api_manage_product.repositories;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import com.dibimbing.api_manage_product.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByIsDeletedFalse();

    Optional<Product> findByIdAndIsDeletedFalse(Long id);

    Optional<Product> findByNameAndIsDeletedFalse(String name);



}
