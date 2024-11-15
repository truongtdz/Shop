package com.example.shop.repository.customer;

import com.example.shop.dto.NewSearch;
import com.example.shop.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepositoryCustomer {
    List<Product> search(NewSearch newSearch);
}
