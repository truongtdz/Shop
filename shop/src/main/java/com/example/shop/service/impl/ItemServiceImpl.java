package com.example.shop.service.impl;

import com.example.shop.entity.Cart;
import com.example.shop.entity.Item;
import com.example.shop.entity.Order;
import com.example.shop.entity.Product;
import com.example.shop.repository.ItemRepository;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public void AddItem(List<Cart> carts, Long orderId) {

        for(Cart cart : carts){
            Item newItem = new Item();

            Product product = productRepository.findById(cart.getProduct().getId()).get();

            Order order = orderRepository.findById(orderId).get();

            newItem.setQuantity(cart.getQuantity());
            newItem.setDiscount(product.getDiscount());
            newItem.setPrice(product.getPrice());
            newItem.setTotal((product.getPrice() * (100 - product.getDiscount()) / 100) * cart.getQuantity());
            newItem.setProduct(product);
            newItem.setOrder(order);

            itemRepository.save(newItem);
        }

    }

    @Override
    public List<Item> getItemByOrderId(Long orderId) {
        return itemRepository.findItemByOrderId(orderId);
    }
}
