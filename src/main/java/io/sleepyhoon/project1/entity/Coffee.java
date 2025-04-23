package io.sleepyhoon.project1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "coffees")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coffee {

    @Id
    @Column(name = "coffee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Setter 테스트용 Setter입니다.
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private Integer price;

    @Setter
    private String img;


    @Builder
    public Coffee(String name, Integer price, String img) {
        this.name = name;
        this.price = price;
        this.img = img;
    }

}
