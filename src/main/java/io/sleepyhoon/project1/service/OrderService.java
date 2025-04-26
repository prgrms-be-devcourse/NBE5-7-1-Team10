package io.sleepyhoon.project1.service;



import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.CoffeeListDto;
import io.sleepyhoon.project1.dto.OrderRequestDto;
import io.sleepyhoon.project1.dto.OrderResponseDto;
import io.sleepyhoon.project1.entity.CoffeeOrder;
import io.sleepyhoon.project1.entity.Order;
import io.sleepyhoon.project1.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
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

    public OrderResponseDto save(OrderRequestDto request)    {
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

        return OrderResponseDto.builder()
                 .id(order.getId())
                 .price(order.getPrice())
                 .email(order.getEmail())
                 .address(order.getAddress())
                 .postNum(order.getPostNum())
                 .coffeeList(toDtoList(coffeeOrders))
                 .build();

    }

    private List<CoffeeListDto> toDtoList(List<CoffeeOrder> coffeeOrders) {
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
                        .coffeeList(toDtoList(order.getCoffeeOrders()))
                        .build());
        }

        return orderResponseDtoList;
    }

    public OrderResponseDto findById(Long id) {
        return orderRepository.findByIdAsDto(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    public void delete(String email, String address) {

        Order findOrder = orderRepository.findByEmailAndAddress(email, address)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orderRepository.deleteById(findOrder.getId());
    }

}
