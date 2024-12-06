package com.example.shop.service.impl;

import com.example.shop.entity.Item;
import com.example.shop.entity.Order;
import com.example.shop.entity.User;
import com.example.shop.repository.ItemRepository;
import com.example.shop.repository.OrderRepository;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Long getCount(){
        return orderRepository.count();
    }

    @Override
    public Long getIcrease() {
        return orderRepository.findAll().stream()
                .mapToLong(o -> Long.valueOf(o.getTotal()))
                .sum();
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    @Override
    public Long AddOrder(User user) {

        Order order = new Order();

        order.setDate(new Date());
        order.setUser(user);

        orderRepository.save(order);

        return  order.getId();
    }

    @Override
    public void UpdateToTalPrice(Long orderId) {
        Long totalPrice = 0L;
        List<Item> items = itemRepository.findItemByOrderId(orderId);
        for(Item item : items){
            totalPrice += item.getTotal();
        }

        Order order = orderRepository.findById(orderId).get();

        order.setTotal(totalPrice);
        order.setQuantity(Long.valueOf(items.size()));

        orderRepository.save(order);
    }

    @Override
    public List<Order> historyBuy(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
