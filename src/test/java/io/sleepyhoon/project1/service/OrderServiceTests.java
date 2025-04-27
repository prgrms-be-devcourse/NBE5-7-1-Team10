package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.OrderResponseDto;
import io.sleepyhoon.project1.entity.Coffee;
import io.sleepyhoon.project1.entity.CoffeeOrder;
import io.sleepyhoon.project1.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderServiceTests {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Test
    @DisplayName("email에 해당하는 주문 리스트 읽기")
    void readAllOrders() throws Exception {

        // given
        Coffee americano = genCoffee("아메리카노", 1500, "1");
        Coffee cappuccino = genCoffee("카푸치노", 3000, "2");

        Order order1 = genOrder("ex@example.com", "경기도", "12345", 4500);
        Order order2 = genOrder("test@test.com", "서울", "12242", 9000);
        Order order3 = genOrder("ex@example.com", "부산", "14442", 13500);

        Field idField = Order.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(order1, 1L);
        idField.set(order2, 2L);
        idField.set(order3, 3L);

        List<CoffeeOrder> coffeeOrders1 = genCoffeeOrderList(genCoffeeOrder(americano, order1, 1), genCoffeeOrder(cappuccino, order1, 1));
        List<CoffeeOrder> coffeeOrders2 = genCoffeeOrderList(genCoffeeOrder(americano, order1, 2), genCoffeeOrder(cappuccino, order1, 2));
        List<CoffeeOrder> coffeeOrders3 = genCoffeeOrderList(genCoffeeOrder(americano, order1, 3), genCoffeeOrder(cappuccino, order1, 3));

        order1.setCoffeeOrders(coffeeOrders1);
        order2.setCoffeeOrders(coffeeOrders2);
        order3.setCoffeeOrders(coffeeOrders3);

        List<Order> orderList = List.of(order1, order3);

        when(orderRepository.findByEmail("ex@example.com")).thenReturn(orderList);

        // when
        List<OrderResponseDto> orders = orderService.findAllOrdersByEmail("ex@example.com");

        // then
        assertThat(orders.size()).isEqualTo(2);
        assertThat(orders.get(0).getId()).isEqualTo(order1.getId());
        assertThat(orders.get(1).getPrice()).isEqualTo(order3.getPrice());
        assertThat(order1.getPrice()).isEqualTo(getPrice(coffeeOrders1));

        assertThat(orders.get(1).getAddress()).isNotEqualTo(order1.getAddress());
        assertThat(orders.get(1).getEmail()).isNotEqualTo(order2.getEmail());

    }


    private Coffee genCoffee(String coffeeName, Integer price, String img) {
        return Coffee.builder()
                .name(coffeeName)
                .price(price)
                .img(img)
                .build();
    }

    private CoffeeOrder genCoffeeOrder(Coffee coffee, Order order, Integer quantity) {
        return CoffeeOrder.builder()
                .coffee(coffee)
                .order(order)
                .quantity(quantity)
                .build();
    }

    private List<CoffeeOrder> genCoffeeOrderList(CoffeeOrder... coffeeOrders) {
        return List.of(coffeeOrders);
    }

    private Order genOrder(String email, String address, String postNum, Integer price) {
        return Order.builder()
                .email(email)
                .address(address)
                .postNum(postNum)
                .price(price)
                .build();
    }

    private Integer getPrice(List<CoffeeOrder> coffeeOrders) {
        Integer price = 0;

        for (CoffeeOrder coffeeOrder : coffeeOrders) {
            price += coffeeOrder.getCoffee().getPrice() * coffeeOrder.getQuantity();
        }
        return price;
    }

}