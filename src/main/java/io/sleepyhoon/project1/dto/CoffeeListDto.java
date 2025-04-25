package io.sleepyhoon.project1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeListDto {
    private String coffeeName;
    private Integer quantity;
}
