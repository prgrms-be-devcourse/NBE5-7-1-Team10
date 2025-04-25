package io.sleepyhoon.project1.service;



import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.CoffeeListDto;
import io.sleepyhoon.project1.dto.OrderDto;
import io.sleepyhoon.project1.dto.OrderRequestDto;
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

    public OrderRequestDto save(OrderRequestDto request)    {
        Long orderId = orderRepository.save(
                Order.builder()
                        .email(request.getEmail())
                        .address(request.getAddress())
                        .postNum(request.getPostNum())
                        .build()
        ).getId();

        OrderRequestDto orderDto = findById(orderId);
        List<CoffeeListDto> coffeeList = orderRepository.findCoffeeListByOrderId(orderId);
        Integer price = orderRepository.getPriceById(orderId);

        orderDto.setCoffeeList(coffeeList);
        orderDto.setPrice(price);

        return orderDto;
    }

    public List<OrderDto> findAllOrdersByEmail(String email) {
        return orderRepository.findByEmail(email);
    }

    public OrderRequestDto findById(Long id) {
        return orderRepository.findDtoById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    public void delete(String email, String address) {

        Order findOrder = orderRepository.findByEmailAndAddress(email, address)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orderRepository.deleteById(findOrder.getId());
    }

}
