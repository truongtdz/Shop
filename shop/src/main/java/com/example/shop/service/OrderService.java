package com.example.shop.service;

import com.example.shop.entity.Order;
import com.example.shop.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    // View count Order
    Long getCount();

    // Total Price
    Long getIcrease();

    // View all Order
    List<Order> getAllOrder();

    // Get Order By orderId;
    Order getOrderById(Long orderId);

    // AddOrder
    Long AddOrder(User user);

    // Update ToTal Price
    void UpdateToTalPrice(Long orderId);

    // Get Order By UserId
    List<Order> historyBuy(Long userId);

}
