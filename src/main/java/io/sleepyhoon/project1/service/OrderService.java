package io.sleepyhoon.project1.service;



import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.CoffeeListDto;
import io.sleepyhoon.project1.dto.OrderRequestDto;
import io.sleepyhoon.project1.entity.CoffeeOrder;
import io.sleepyhoon.project1.entity.Order;
import io.sleepyhoon.project1.event.OrderCreatedEvent;
import io.sleepyhoon.project1.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final CoffeeOrderService coffeeOrderService;
    private final ApplicationEventPublisher publisher;


    public OrderRequestDto save(OrderRequestDto request)    {
        Order order = orderRepository.save(
                Order.builder()
                        .email(request.getEmail())
                        .address(request.getAddress())
                        .postNum(request.getPostNum())
                        .price(request.getPrice())
                        .build()
        );

        List<CoffeeOrder> coffeeOrders = coffeeOrderService.genCoffeeOrderList(request.getCoffeeList(), order);

        order.setCoffeeOrders(coffeeOrders);

        OrderRequestDto orderRequestDto = new OrderRequestDto(order.getPrice(), order.getEmail(), order.getAddress(), order.getPostNum());
        List<CoffeeListDto> coffeeList = orderRequestDto.getCoffeeList();

        for (CoffeeOrder coffeeOrder : coffeeOrders) {
            coffeeList.add(new CoffeeListDto(coffeeOrder.getCoffee().getName(), coffeeOrder.getQuantity()));
        }
        publisher.publishEvent(new OrderCreatedEvent(order));
        return orderRequestDto;
    }

    public List<OrderRequestDto> findAllOrdersByEmail(String email) {
        List<Order> orderList = orderRepository.findByEmail(email);

        List<OrderRequestDto> orderRequestDtoList = new ArrayList<>();

        for (Order order : orderList) {
            OrderRequestDto orderRequestDto = new OrderRequestDto(order.getPrice(), order.getEmail(), order.getAddress(), order.getPostNum());
            orderRequestDto.setCoffeeList(orderRepository.findCoffeeListByOrderId(order.getId()));
            log.info("orderRepository.findCoffeeListByOrderId(order.getId()) = {}", orderRepository.findCoffeeListByOrderId(order.getId()));
            orderRequestDtoList.add(orderRequestDto);
        }

        return orderRequestDtoList;
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