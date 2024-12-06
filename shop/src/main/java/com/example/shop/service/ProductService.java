package com.example.shop.service;

import com.example.shop.dto.NewSearch;
import com.example.shop.entity.Cart;
import com.example.shop.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {
    // View count Product
    int getCount();

    // View All Product
    List<Product> getAllProduct();

    // View Product the newest
    public List<Product> getProductNewest();

    // View Product the newest
    public List<Product> getProductSale();

    // View product by Id
    Product getProductById(Long productId);

    // Search
    List<Product> searchProduct(NewSearch newSearch);

    // Create Product
    boolean createProduct(Product newProduct, MultipartFile image);

    // Update Product
    boolean updateProduct(Product updateProduct, Long productId, MultipartFile image);

    void updateQuantityProduct(List<Cart> carts);

    // Delete Product
    void deleteProduct(Long productId);

    // Add Image
    String addImage(MultipartFile image);

    // Find Product
    List<Product> searchProductByName(String name);

}
