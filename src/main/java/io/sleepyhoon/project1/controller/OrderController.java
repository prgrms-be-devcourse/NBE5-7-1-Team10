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
    public ResponseEntity<ApiResponse<List<OrderDto>>> getAllOrders(@RequestParam String email) {
        List<OrderDto> orders = orderService.findByEmailAllOrders(email);
        return ResponseEntity.ok(new ApiResponse<>(orders, "조회 성공", 200));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> saveOrder(@RequestBody OrderDto orderDto) {
        Long order = orderService.save(orderDto);
        return ResponseEntity.ok(new ApiResponse<>(order, "주문 성공", 201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id);

        OrderDto orderDto = OrderDto.builder()
                .email(order.getEmail())
                .address(order.getAddress())
                .postNum(order.getPostNum())
                .build();
        return ResponseEntity.ok(new ApiResponse<>(orderDto, "조회 성공", 200));
    }
}
