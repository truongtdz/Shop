package com.example.shop.service.impl;

import com.example.shop.dto.Login;
import com.example.shop.entity.User;
import com.example.shop.repository.UserRepository;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Long getCount() {
        return userRepository.count();
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean createUser(User newUser, MultipartFile file) {
        if(userRepository.findByUsername(newUser.getUsername()) != null){
            return false;
        } else{
            if(file != null && !file.isEmpty()){
                newUser.setImage(addImage(file));
            }
            userRepository.save(newUser);
            return true;
        }

    }

    @Override
    public void updateUser(User updateUser, Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).get();

        if(updateUser.getPassword() != null){
            user.setPassword(updateUser.getPassword());
        }
        user.setEmail(updateUser.getEmail());
        user.setPhone(updateUser.getPhone());
        user.setCity(updateUser.getCity());
        user.setDistrict(updateUser.getDistrict());
        user.setAddress(updateUser.getAddress());
        user.setFullName(updateUser.getFullName());
        user.setGender(updateUser.getGender());
        if(file != null && !file.isEmpty()){
            user.setImage(addImage(file));
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean login(Login request) {
        User user = userRepository.findByUsername(request.getUsername());

        if(user != null && user.getPassword().equals(request.getPassword())){
            return true;
        } else return false;
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
}
