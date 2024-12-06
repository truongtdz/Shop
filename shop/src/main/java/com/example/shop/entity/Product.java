package com.example.shop.entity;

import com.example.shop.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "status")
    String status;

    @Column(name = "name")
    String name;

    @Column(name = "price")
    Long price;

    @Column(name = "discount")
    Long discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    CategoryEnum category;

    @Column(name = "image")
    String image;

    @Column(name = "stock")
    Long stock;

    @Column(name = "description")
    String description;

    @Column(name = "screen")
    String screen;

    @Column(name = "storage")
    String storage;

    @Column(name = "ram")
    String ram;

    @Column(name = "battery")
    String battery;

    @Column(name = "create_date")
    Date createDate;

    @OneToMany(mappedBy = "product")
    List<Comment> comments;

    @OneToMany(mappedBy = "product")
    List<Item> items;

    @OneToMany(mappedBy = "product")
    List<Cart> carts;
}
