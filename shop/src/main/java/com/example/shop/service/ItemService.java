package com.example.shop.service;

import com.example.shop.entity.Cart;
import com.example.shop.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    // Add Item
    void AddItem(List<Cart> carts, Long orderId);

    // Get Item By Order Id
    List<Item> getItemByOrderId(Long orderId);
}
