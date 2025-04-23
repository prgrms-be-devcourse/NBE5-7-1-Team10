package io.sleepyhoon.project1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
