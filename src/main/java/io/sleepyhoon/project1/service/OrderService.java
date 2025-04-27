package io.sleepyhoon.project1.service;



import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.CoffeeListDto;
import io.sleepyhoon.project1.dto.OrderRequestDto;
import io.sleepyhoon.project1.dto.OrderResponseDto;
import io.sleepyhoon.project1.entity.CoffeeOrder;
import io.sleepyhoon.project1.entity.Order;
import io.sleepyhoon.project1.event.OrderCreatedEvent;
import io.sleepyhoon.project1.exception.OrderNotFoundException;
import io.sleepyhoon.project1.exception.OrderOwnerMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final CoffeeOrderService coffeeOrderService;
    private final ApplicationEventPublisher publisher;


    public OrderResponseDto save(OrderRequestDto request)    {
        Order order = orderRepository.save(
                Order.builder()
                        .email(request.getEmail())
                        .address(request.getAddress())
                        .postNum(request.getPostNum())
                        .price(0)
                        .build()
        );

        List<CoffeeOrder> coffeeOrders = coffeeOrderService.genCoffeeOrderList(request.getCoffeeList(), order);

        Integer totalPrice = getTotalPrice(coffeeOrders);

        order.setPrice(totalPrice);
        order.setCoffeeOrders(coffeeOrders);

        publisher.publishEvent(new OrderCreatedEvent(order));

        return OrderResponseDto.builder()
                 .id(order.getId())
                 .price(order.getPrice())
                 .email(order.getEmail())
                 .address(order.getAddress())
                 .postNum(order.getPostNum())
                 .coffeeList(convertToDtoList(coffeeOrders))
                 .build();

    }

    private Integer getTotalPrice(List<CoffeeOrder> coffeeOrders) {
        Integer totalPrice = 0;
        for (CoffeeOrder coffeeOrder : coffeeOrders) {
            totalPrice += coffeeOrder.getSubtotalPrice();
        }
        return totalPrice;
    }

    private List<CoffeeListDto> convertToDtoList(List<CoffeeOrder> coffeeOrders) {
        List<CoffeeListDto> coffeeListDtos = new ArrayList<>();

        for (CoffeeOrder coffeeOrder : coffeeOrders) {
            coffeeListDtos.add(new CoffeeListDto(coffeeOrder.getCoffee().getName(), coffeeOrder.getQuantity()));
        }

        return coffeeListDtos;
    }

    public List<OrderResponseDto> findAllOrdersByEmail(String email) {
        List<Order> orderList = orderRepository.findByEmail(email);

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for (Order order : orderList) {
            orderResponseDtoList.add(
                    OrderResponseDto.builder()
                        .id(order.getId())
                        .email(order.getEmail())
                        .address(order.getAddress())
                        .postNum(order.getPostNum())
                        .price(order.getPrice())
                        .coffeeList(convertToDtoList(order.getCoffeeOrders()))
                        .build());
        }

        return orderResponseDtoList;
    }

    public OrderResponseDto findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        return OrderResponseDto.builder()
                .id(order.getId())
                .email(order.getEmail())
                .address(order.getAddress())
                .postNum(order.getPostNum())
                .price(order.getPrice())
                .coffeeList(convertToDtoList(order.getCoffeeOrders()))
                .build();
    }

    public void delete(Long id, String email) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        if ( !order.getEmail().equals(email) ) {
            throw new OrderOwnerMismatchException("OrderOwner Mismatch: " + email + " != " + order.getEmail());
        }

        orderRepository.delete(order);
    }

}