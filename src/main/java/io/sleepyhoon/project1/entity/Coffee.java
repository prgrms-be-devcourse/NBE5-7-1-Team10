package io.sleepyhoon.project1.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "coffees")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coffee {

    @Id
    @Column(name = "coffee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter //테스트용 Setter입니다.
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private Integer price;

    public Coffee(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
