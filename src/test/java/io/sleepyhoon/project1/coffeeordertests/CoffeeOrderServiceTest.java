package io.sleepyhoon.project1.coffeeordertests;

import io.sleepyhoon.project1.dao.CoffeeOrderRepository;
import io.sleepyhoon.project1.dao.CoffeeRepository;
import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.CoffeeOrderDto;
import io.sleepyhoon.project1.dto.CoffeeRequestDto;
import io.sleepyhoon.project1.entity.Coffee;
import io.sleepyhoon.project1.entity.CoffeeOrder;
import io.sleepyhoon.project1.entity.Order;
import io.sleepyhoon.project1.service.CoffeeOrderService;
import io.sleepyhoon.project1.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
public class CoffeeOrderServiceTest {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;
    @Autowired
    private OrderRepository orderRepository;


    private Coffee genCoffee(String coffeeName) {

        Coffee coffee = Coffee.builder()
                .name(coffeeName)
                .price((int) (Math.random() * 10000))
                .img("imgLink")
                .build();

        return coffeeRepository.save(coffee);
    }

    private CoffeeOrder genCoffeeOrder(Coffee coffee, Order order, Integer quantity) {
        CoffeeOrder coffeeOrder = CoffeeOrder.builder()
                .coffee(coffee)
                .order(order)
                .quantity(quantity)
                .build();

        return coffeeOrderRepository.save(coffeeOrder);
    }

    @Test
    @DisplayName("CoffeeOrder 생성 테스트")
    void coffeeOrderSaveTest() throws Exception {

        //given
        Coffee test1 = genCoffee("test1");
        Coffee test2 = genCoffee("test2");

        Order testOrder = Order.builder()
                .email("coffeeordertests@gmail.com")
                .address("분당")
                .postNum("456745")
                .build();
        orderRepository.save(testOrder);

        Map<String, Integer> coffeeOrderRequestDtoMap = Map.of(
                "test1",3
                ,"test2",2
        );

        CoffeeOrder coffeeOrder1 = genCoffeeOrder(test1, testOrder, 3);
        CoffeeOrder coffeeOrder2 = genCoffeeOrder(test2, testOrder, 2);



        List<CoffeeOrder> expectedCoffeeOrders = new ArrayList<>(List.of(
                coffeeOrder1, coffeeOrder2
        ));
        //순서보장이 안되서 테스트 실패하는 경우가 있어서 정렬 후 검증
        expectedCoffeeOrders.sort(Comparator.comparing(o -> o.getCoffee().getName()));

        //when
        List<CoffeeOrder> actualCoffeeOrders=
                coffeeOrderService.save(coffeeOrderRequestDtoMap, testOrder).getCoffeeOrders();
        //순서보장이 안되서 테스트 실패하는 경우가 있어서 정렬 후 검증
        actualCoffeeOrders.sort(Comparator.comparing(o -> o.getCoffee().getName()));


        //then
        assertEquals(expectedCoffeeOrders.size(), actualCoffeeOrders.size());
        for (int i = 0; i < expectedCoffeeOrders.size(); i++) {
            assertEquals(expectedCoffeeOrders.get(i).getCoffee().getName(), actualCoffeeOrders.get(i).getCoffee().getName());
            assertEquals(expectedCoffeeOrders.get(i).getQuantity(), actualCoffeeOrders.get(i).getQuantity());
        }

        log.info("actualCoffeeOrders = {}", actualCoffeeOrders);

    }

}
