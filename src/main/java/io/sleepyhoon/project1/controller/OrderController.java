package io.sleepyhoon.project1.controller;

import io.sleepyhoon.project1.dto.ApiResponse;
import io.sleepyhoon.project1.dto.OrderDto;
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
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrders(@RequestParam String email) {
        List<OrderDto> orders = orderService.findAllOrdersByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(orders, "조회 성공", 200));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> saveOrder(@RequestBody OrderDto orderDto) {
        Long order = orderService.save(orderDto);
        return ResponseEntity.ok(new ApiResponse<>(order, "주문 성공", 201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(orderService.findById(id), "조회 성공", 200));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOrder(@RequestParam String email, @RequestParam String address) {
        orderService.delete(email, address);
        return ResponseEntity.noContent().build();
    }

}
