package io.sleepyhoon.project1.dto;

import io.sleepyhoon.project1.entity.Coffee;
import io.sleepyhoon.project1.entity.Order;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoffeeOrderDto {

    private Coffee coffee;
    private Integer quantity;
    private Order order;

    @Builder
    public CoffeeOrderDto(Coffee coffee, Integer quantity, Order order) {
        this.coffee = coffee;
        this.quantity = quantity;
        this.order = order;
    }
}
