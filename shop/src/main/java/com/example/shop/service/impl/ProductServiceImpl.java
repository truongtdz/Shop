package com.example.shop.service.impl;

import com.example.shop.dto.NewSearch;
import com.example.shop.entity.Cart;
import com.example.shop.entity.Product;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Long getCount(){
        return productRepository.count();
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).get();
    }

    @Override
    public List<Product> searchProduct(NewSearch newSearch) {
        return productRepository.search(newSearch);
    }

    @Override
    public boolean createProduct(Product newProduct, MultipartFile image) {
        if (productRepository.findByName(newProduct.getName()) != null) {
            return false;
        } else {
            if(image != null && !image.isEmpty()){
                newProduct.setImage(addImage(image));
            }
            newProduct.setCreateDate(new Date());
            productRepository.save(newProduct);
            return true;
        }
    }

    @Override
    public boolean updateProduct(Product updateProduct, Long productId, MultipartFile image) {
        Product oldProduct = productRepository.findById(productId).get();

        if(updateProduct.getName() != null && !updateProduct.getName().isEmpty()){
            if (productRepository.findByName(updateProduct.getName()) != null) {
                return false;
            }
            oldProduct.setName(updateProduct.getName());
        }
        oldProduct.setCategory(updateProduct.getCategory());
        if(updateProduct.getPrice() != null ){
            oldProduct.setPrice(updateProduct.getPrice());
        }
        if(updateProduct.getDiscount() != null ){
            oldProduct.setDiscount(updateProduct.getDiscount());
        }
        if(updateProduct.getStock() != null){
            oldProduct.setStock(updateProduct.getStock());
        }
        if(updateProduct.getDescription() != null && !updateProduct.getDescription().isEmpty()){
            oldProduct.setDescription(updateProduct.getDescription());
        }
        if(image != null && !image.isEmpty()){
            oldProduct.setImage(addImage(image));
        }

        
        productRepository.save(oldProduct);
        return true;

    }

    @Override
    public void updateQuantityProduct(List<Cart> carts) {
        for(Cart cart : carts){
            Product product = productRepository.findById(cart.getProduct().getId()).get();

            product.setStock(product.getStock() - cart.getQuantity());

            productRepository.save(product);
        }

    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getProductNewest() {
        List<Product> products = productRepository.findAll().stream()
                .sorted(Comparator.comparing(Product::getCreateDate).reversed())  // Sắp xếp theo ngày tạo từ mới đến cũ
                .limit(8)  // Lấy 8 sản phẩm mới nhất
                .collect(Collectors.toList());  // Thu thập kết quả vào danh sách

        return products;  // Trả về danh sách sản phẩm mới nhất
    }

    @Override
    public List<Product> getProductSale() {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparing(Product::getDiscount).reversed())  // Sắp xếp theo ngày tạo từ mới đến cũ
                .limit(8)  // Lấy 8 sản phẩm mới nhất
                .collect(Collectors.toList());  // Thu thập kết quả vào danh sách
    }

    @Override
    public String addImage(MultipartFile image){
        String result = "";
        try {
            if (!image.isEmpty()) {
                String folder = "D:/_VegetableShop/ShopSale/shop/src/main/resources/static/img/";
                File directory = new File(folder);
                if (!directory.exists()) {
                    directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
                }

//                    // Kiểm tra loại file
//                    String contentType = image.getContentType();
//                    if (!contentType.startsWith("image/")) {
//                        throw new IllegalArgumentException("File không phải là ảnh.");
//                    }

                // Tạo tên file duy nhất
                String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path path = Paths.get(folder + imageName);
                Files.write(path, image.getBytes());

                result += "/img/" + imageName;
            }
        } catch (IOException e) {
            throw new RuntimeException("Có lỗi khi lưu ảnh: " + e.getMessage());
        }

        return result;
    }

    @Override
    public List<Product> searchProductByName(String name) {
        return productRepository.findByNameContainingOrDescriptionContaining(name, name);
    }
}
