package io.sleepyhoon.project1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponseDto {

    @JsonProperty("coffee-list")
    private List<CoffeeListDto> coffeeList;

    private Long id;
    private Integer price;
    private String email;
    private String address;
    private String postNum;

    @Builder
    public OrderResponseDto(List<CoffeeListDto> coffeeList, Long id, Integer price, String email, String address, String postNum) {
        this.coffeeList = coffeeList;
        this.id = id;
        this.price = price;
        this.email = email;
        this.address = address;
        this.postNum = postNum;
    }
}
