package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "quantity")
    Long quantity;

    @Column(name = "price")
    Long price;

    @Column(name = "discount")
    Long discount;

    @Column(name = "total")
    Long total;

    @ManyToOne
    @JoinColumn(name = "product_id")  
    Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

}
