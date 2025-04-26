package io.sleepyhoon.project1.controller;

import io.sleepyhoon.project1.dto.ApiResponse;
import io.sleepyhoon.project1.dto.OrderDto;
import io.sleepyhoon.project1.dto.OrderRequestDto;
import io.sleepyhoon.project1.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderRequestDto>>> getAllOrders(@RequestParam String email) {
        List<OrderRequestDto> orders = orderService.findAllOrdersByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(orders, "조회 성공", 200));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderRequestDto>> saveOrder(@RequestBody OrderRequestDto request) {
        OrderRequestDto order = orderService.save(request);
        return ResponseEntity.ok(new ApiResponse<>(order, "주문 성공", 201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderRequestDto>> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(orderService.findById(id), "조회 성공", 200));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOrder(@RequestParam String email, @RequestParam String address) {
        orderService.delete(email, address);
        return ResponseEntity.noContent().build();
    }

}
