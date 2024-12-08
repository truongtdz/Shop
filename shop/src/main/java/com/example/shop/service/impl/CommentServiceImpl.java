package com.example.shop.service.impl;

import com.example.shop.entity.Comment;
import com.example.shop.repository.CommentRepository;
import com.example.shop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> findByProductId(Long productId) {
        return commentRepository.findByProductId(productId);
    }

    @Override
    public void AddComment(Comment newComment) {

        newComment.setCreateDate(new Date());
        commentRepository.save(newComment);

    }
}
