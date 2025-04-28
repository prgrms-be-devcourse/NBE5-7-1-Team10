package io.sleepyhoon.project1.entity;

import io.sleepyhoon.project1.service.CoffeeImgService;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "coffees")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coffee {

    @Id
    @Column(name = "coffee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true)
    private String name;

    @Setter
    @Column(nullable = false)
    private Integer price;

    @Setter
    @OneToMany(mappedBy = "coffee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoffeeImg> images = new ArrayList<>();

    @Setter
    @Column(nullable = false)
    private Integer stock;

    @Builder
    public Coffee(String name, Integer price, List<CoffeeImg> images, Integer stock) {
        this.name = name;
        this.price = price;
        this.images = images != null ? images : new ArrayList<>();
        this.stock = stock;
    }

}
