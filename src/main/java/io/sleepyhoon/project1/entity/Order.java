package io.sleepyhoon.project1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String postNum;

    @Column(nullable = false)
    private LocalDateTime orderedAt = LocalDateTime.now();

    @Setter
    @Column(nullable = false)
    private Boolean isProcessed = false;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<CoffeeOrder> coffeeOrders;

    @Builder
    public Order(String email, String address, String postNum) {
        this.email = email;
        this.address = address;
        this.postNum = postNum;
    }
}
