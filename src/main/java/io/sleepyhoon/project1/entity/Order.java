package io.sleepyhoon.project1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Member member;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String postNum;

    @Setter
    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private LocalDateTime orderedAt = LocalDateTime.now();

    @Setter
    @Column(nullable = false)
    private Boolean isProcessed = false;

    @Setter
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoffeeOrder> coffeeOrders = new ArrayList<>();

    @Builder
    public Order(Member member, String address, String postNum, Integer price) {
        this.member = member;
        this.address = address;
        this.postNum = postNum;
        this.price = price;
    }
}
