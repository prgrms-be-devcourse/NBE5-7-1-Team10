package io.sleepyhoon.project1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponseDto {

    @JsonProperty("coffee-list")
    private List<CoffeeListDto> coffeeList = new ArrayList<>();

    private Long id;
    private Integer price;
    private String email;
    private String address;
    private String postNum;

    public OrderResponseDto(Long id, Integer price, String email, String address, String postNum) {
        this.id = id;
        this.price = price;
        this.email = email;
        this.address = address;
        this.postNum = postNum;
    }

}
