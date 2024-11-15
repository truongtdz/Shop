package com.example.shop.service.impl;

import com.example.shop.entity.Cart;
import com.example.shop.entity.Product;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Cart> getCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Long getCountByUserId(Long userId) {
        return Long.valueOf(cartRepository.findByUserId(userId).size());
    }

    @Override
    public boolean addCart(Long userId, Long productId){
        if(cartRepository.findByUserIdAndProductId(userId, productId) != null) {
            return false;
        }
        Cart newCart = Cart.builder()
                .user((userRepository.findById(userId)).get())
                .product(productRepository.findById(productId).get())
                .quantity(Long.valueOf("1"))
                .build();

        cartRepository.save(newCart);
        return true;
    }

    @Override
    @Transactional
    public void removeProductToCart(Long userId, Long productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    @Transactional
    public void removeCartByUserId(Long userId) {
        cartRepository.deleteByUserId(userId);
    }

    @Override
    public boolean replaceQuantityProduct(Long userId, Long productId, Long quantityReplace){
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId);
        Product product = productRepository.findById(productId).get();

        if(product.getStock() < (cart.getQuantity() + quantityReplace)){
            return false;
        }
        cart.setQuantity(cart.getQuantity() + quantityReplace);

        cartRepository.save(cart);

        return true;
    }
}
