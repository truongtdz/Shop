package com.example.shop.service;

import com.example.shop.dto.Login;
import com.example.shop.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {
    // View count User
    int getCount();

    // View ALL User
    List<User> getAllUser();

    // View User By Id
    User getUserById(Long userId);

    // View User By Username
    User getUserByUsername(String username);

    // Create User
    boolean createUser(User newUser, MultipartFile file);

    // Update User
    void updateUser(User updateUser, Long userId, MultipartFile file);

    // Delete User
    void deleteUser(Long userId);

    // Login User
    boolean login(Login request);

    // Add Image
    String addImage(MultipartFile image);

}
