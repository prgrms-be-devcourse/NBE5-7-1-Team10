package io.sleepyhoon.project1.service;



import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.OrderDto;
import io.sleepyhoon.project1.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;

    public Long save(OrderDto orderDto) {

        Optional<Order> orderOptional = orderRepository.findByEmailAndAddress(orderDto.getEmail(), orderDto.getAddress());

        return orderOptional.orElseGet(() -> orderRepository.save(
                Order.builder()
                        .email(orderDto.getEmail())
                        .address(orderDto.getAddress())
                        .postNum(orderDto.getPostNum())
                        .build()
        )).getId();
    }

    public List<OrderDto> findByEmailAllOrders(String email) {
        List<Order> orders = orderRepository.findByEmail(email);

        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            orderDtos.add(
                    OrderDto.builder()
                            .email(order.getEmail())
                            .address(order.getAddress())
                            .postNum(order.getPostNum())
                    .build());
        }
        
        return orderDtos;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));

    }

}
