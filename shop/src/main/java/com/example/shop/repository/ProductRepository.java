package com.example.shop.repository;

import com.example.shop.entity.Product;

import java.util.List;

import com.example.shop.repository.customer.ProductRepositoryCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustomer {
    Product findByNameAndStatus(String name, String status);
    Product findByIdAndStatus(Long id, String status);
    List<Product> findByStatus(String status);
    List<Product> findByNameContainingOrDescriptionContainingAndStatus(String name, String description, String status);
}
