package io.sleepyhoon.project1.service;



import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.OrderDto;
import io.sleepyhoon.project1.entity.Order;
import io.sleepyhoon.project1.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<OrderDto> findAllOrdersByEmail(String email) {
        return orderRepository.findByEmail(email);
    }

    public OrderDto findById(Long id) {
        return orderRepository.findDtoById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

    }

    public void delete(String email, String address) {

        Order findOrder = orderRepository.findByEmailAndAddress(email, address)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orderRepository.deleteById(findOrder.getId());
    }

}
