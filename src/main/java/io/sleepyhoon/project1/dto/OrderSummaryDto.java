package io.sleepyhoon.project1.dto;


import io.sleepyhoon.project1.entity.CoffeeOrder;
import io.sleepyhoon.project1.entity.Order;

import java.time.LocalDateTime;

public record OrderSummaryDto(
        Long id,
        String email,
        int totalPrice,
        LocalDateTime orderedAt
) {

    public static OrderSummaryDto from(Order order) {
        int sum = order.getCoffeeOrders()
                .stream()
                .mapToInt(co ->
                        co.getCoffee().getPrice()
                                * co.getQuantity())
                .sum();

        return new OrderSummaryDto(
                order.getId(),
                order.getEmail(),
                sum,
                order.getOrderedAt()
        );
    }
}