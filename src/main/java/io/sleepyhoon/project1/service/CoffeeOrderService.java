package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.CoffeeOrderRepository;
import io.sleepyhoon.project1.dto.CoffeeListDto;
import io.sleepyhoon.project1.dto.CoffeeOrderDto;
import io.sleepyhoon.project1.entity.Coffee;
import io.sleepyhoon.project1.entity.CoffeeOrder;
import io.sleepyhoon.project1.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CoffeeOrderService {
    private final CoffeeService coffeeService;
    private final CoffeeOrderRepository coffeeOrderRepository;

    public List<CoffeeOrder> genCoffeeOrderList(List<CoffeeListDto> requestCoffeeList, Order orderRequest) {

        List<CoffeeOrder> responseCoffeeOrderList = new ArrayList<>();
        for(CoffeeListDto coffeeListDto : requestCoffeeList) {

            String coffeeName = coffeeListDto.getCoffeeName();
            Integer coffeeQuantity = coffeeListDto.getQuantity();

            Coffee coffeeInOrder = coffeeService.findFirstCoffeeByName(coffeeName);

            CoffeeOrder coffeeOrder = CoffeeOrder.builder()
                    .coffee(coffeeInOrder)
                    .order(orderRequest)
                    .quantity(coffeeQuantity)
                    .build();

            //CoffeeOrder saved = coffeeOrderRepository.save(coffeeOrder);
            responseCoffeeOrderList.add(coffeeOrder);
        }
        return responseCoffeeOrderList;
    }


}


