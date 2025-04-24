package io.sleepyhoon.project1.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CoffeeRequestDto {
    private String name;
    private Integer price;
    private String img;

    @Builder
    public CoffeeRequestDto(String name, Integer price, String img) {
        this.name = name;
        this.price = price;
        this.img = img;
    }
}
