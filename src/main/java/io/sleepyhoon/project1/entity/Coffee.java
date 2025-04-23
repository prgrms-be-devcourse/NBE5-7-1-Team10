package io.sleepyhoon.project1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
