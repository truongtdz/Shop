package com.example.shop.entity;

import com.example.shop.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "status")
    String status;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "city")
    String city;

    @Column(name = "district")
    String district;

    @Column(name = "address")
    String address;

    @Column(name = "image")
    String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    GenderEnum gender;

    @OneToMany(mappedBy = "user")
    List<Order> orders;

    @OneToMany(mappedBy = "user")
    List<Comment> comments;

    @OneToMany(mappedBy = "user")
    List<Cart> carts;
}
