package io.sleepyhoon.project1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoffeeResponseDto {
    private Long id;
    private String name;
    private Integer price;

    @Builder
    public CoffeeResponseDto(Long id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
