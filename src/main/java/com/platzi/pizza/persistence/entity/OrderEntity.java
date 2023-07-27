package com.platzi.pizza.persistence.entity;

import jakarta.persistence.*;
import org.apache.catalina.LifecycleState;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pizza_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer orderId;

    @Column(nullable = false, length = 15)
    private String customerId;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime date;

    @Column(nullable = false, columnDefinition = "DECIMAL(6,2)")
    private Double total;

    @Column(nullable = false, columnDefinition = "CHAR(1)")
    private String method;

    @Column(length = 200)
    private String note;


    @OneToOne
    @JoinColumn(name = "customerId", updatable = false, insertable = false)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> items;
}
