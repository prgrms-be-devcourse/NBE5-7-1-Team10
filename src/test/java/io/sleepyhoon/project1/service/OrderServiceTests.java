package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dto.OrderDto;
import io.sleepyhoon.project1.entity.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class OrderServiceTests {

    @Autowired
    OrderService orderService;

    @Test
    @Rollback(false)
    @DisplayName("Order 저장 테스트")
    void orderSaveTest() throws Exception {

        OrderDto orderDto = OrderDto.builder()
                .email("h1@h1.com")
                .address("경기도")
                .postNum("12345")
                .build();

        Order saved = orderService.save(orderDto);
        assertThat(saved).isNotNull();
    }

    @Test
    @DisplayName("email에 해당하는 주문 리스트 읽기")
    void readAllOrders() throws Exception {

        OrderDto orderDto1 = OrderDto.builder()
                .email("h1@h1.com")
                .address("화성")
                .postNum("12345")
                .build();
        orderService.save(orderDto1);
        OrderDto orderDto2 = OrderDto.builder()
                .email("h1@h1.com")
                .address("경기도")
                .postNum("12345")
                .build();
        orderService.save(orderDto2);
        OrderDto orderDto3 = OrderDto.builder()
                .email("h1@h1.com")
                .address("서울")
                .postNum("12345")
                .build();
        orderService.save(orderDto3);


        List<Order> orderList = orderService.findAllOrdersByEmail("h1@h1.com");

        assertThat(orderList).isNotNull();
        assertThat(orderList.size()).isEqualTo(3);

    }

}