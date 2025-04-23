package io.sleepyhoon.project1.service;



import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.OrderDto;
import io.sleepyhoon.project1.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;

    public Order save(OrderDto orderDto) {

        Optional<Order> orderOptional = orderRepository.findByEmailAndAddress(orderDto.getEmail(), orderDto.getAddress());

        return orderOptional.orElseGet(() -> orderRepository.save(
                Order.builder()
                        .email(orderDto.getEmail())
                        .address(orderDto.getAddress())
                        .postNum(orderDto.getPostNum())
                        .build()
        ));
    }

    public List<Order> findByEmailAllOrders(String email) {
        return orderRepository.findByEmail(email);
    }


}
