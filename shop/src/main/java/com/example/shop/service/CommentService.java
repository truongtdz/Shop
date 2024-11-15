package com.example.shop.service;

import com.example.shop.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    // Find By UserId
    List<Comment> findByProductId(Long productId);

    // Add Comment
    void AddComment(Comment newComment);
}
