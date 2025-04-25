package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.CoffeeOrderRepository;
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

    public List<CoffeeOrder> genCoffeeOrderList(Map<String, Integer> coffeeOrderMap, Order orderRequest) {

        List<CoffeeOrder> coffeeOrderList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : coffeeOrderMap.entrySet()) {
            String coffeeName = entry.getKey();
            Integer coffeeQuantity = entry.getValue();

            Coffee coffeeInOrder = coffeeService.findFirstCoffeeByName(coffeeName);

            CoffeeOrder coffeeOrder = CoffeeOrder.builder()
                    .coffee(coffeeInOrder)
                    .order(orderRequest)
                    .quantity(coffeeQuantity)
                    .build();

            //CoffeeOrder saved = coffeeOrderRepository.save(coffeeOrder);
            coffeeOrderList.add(coffeeOrder);
        }
        return coffeeOrderList;
    }

}


