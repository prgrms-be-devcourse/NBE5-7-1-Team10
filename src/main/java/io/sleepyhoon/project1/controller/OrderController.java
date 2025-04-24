package io.sleepyhoon.project1.controller;

import io.sleepyhoon.project1.dto.ApiResponse;
import io.sleepyhoon.project1.dto.OrderDto;
import io.sleepyhoon.project1.entity.Order;
import io.sleepyhoon.project1.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders(@RequestParam String email) {
        List<Order> orders = orderService.findByEmailAllOrders(email);
        return ResponseEntity.ok(new ApiResponse<>(orders, "조회 성공", 200));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> saveOrder(@RequestBody OrderDto orderDto) {
        Order order = orderService.save(orderDto);
        return ResponseEntity.ok(new ApiResponse<>(order, "주문 성공", 201));
    }
}
