package com.example.shop.repository;

import com.example.shop.entity.Product;

import java.util.List;

import com.example.shop.repository.customer.ProductRepositoryCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustomer {
    Product findByName(String name);

    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
}
