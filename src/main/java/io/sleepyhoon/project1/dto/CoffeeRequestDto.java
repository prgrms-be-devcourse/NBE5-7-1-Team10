package io.sleepyhoon.project1.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoffeeRequestDto {
    private String name;
    private Integer price;
//    private String img;

    @Builder
    public CoffeeRequestDto(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
