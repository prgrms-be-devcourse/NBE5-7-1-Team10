package io.sleepyhoon.project1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    @JsonProperty("coffee-list") // JSON의 키와 다른 경우 매핑
    private List<CoffeeListDto> coffeeList = new ArrayList<>();

    private Integer price;
    private String email;
    private String address;
    private String postNum;

}

