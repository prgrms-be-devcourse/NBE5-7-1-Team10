package io.sleepyhoon.project1.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "coffee_orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoffeeOrder {
    @Id
    @Column(name = "coffee_order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coffee_id")
    private Coffee coffee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer subtotalPrice;

    @Builder
    public CoffeeOrder(Coffee coffee, Order order, Integer quantity) {
        this.coffee = coffee;
        this.order = order;
        this.quantity = quantity;
        this.subtotalPrice = coffee.getPrice() * quantity;
    }

}
