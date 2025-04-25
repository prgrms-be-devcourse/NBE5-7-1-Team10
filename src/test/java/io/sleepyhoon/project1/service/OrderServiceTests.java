package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.OrderDto;
import io.sleepyhoon.project1.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderServiceTests {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    static OrderDto dto;
    static Order savedOrder;

    @BeforeEach
    void init() throws Exception {
        // given
        dto = new OrderDto("ex@example.com", "경기도", "12345");

        savedOrder = Order.builder()
                .email(dto.getEmail())
                .address(dto.getAddress())
                .postNum(dto.getPostNum())
                .build();

        // Order객체 Id 강제 설정
        Field idField1 = Order.class.getDeclaredField("id");
        idField1.setAccessible(true);
        idField1.set(savedOrder, 1L);
    }

    @Test
    @DisplayName("입력한 주문이 존재할 때 저장되는 경우")
    void saveExistOrderTest() throws Exception {

        when(orderRepository.findByEmailAndAddress(dto.getEmail(),dto.getAddress())).thenReturn(Optional.of(savedOrder));

        // when
        Long firstCall = orderService.save(dto);
        Long secondCall = orderService.save(dto);

        // then
        assertThat(firstCall).isEqualTo(secondCall);
        verify(orderRepository, never()).save(any());

    }

    @Test
    @DisplayName("주문이 존재하지 않을 때 저장되는 경우")
    void saveNotExistOrderTest() throws Exception {

        when(orderRepository.findByEmailAndAddress(dto.getEmail(),dto.getAddress())).thenReturn(Optional.empty());
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // when
        Long result = orderService.save(dto);

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    @DisplayName("email에 해당하는 주문 리스트 읽기")
    void readAllOrders() throws Exception {

        // given
        OrderDto dto1 = new OrderDto("ex@example.com", "경기도", "12345");
        OrderDto dto2 = new OrderDto("ex@example.com", "서울", "23132");
        OrderDto dto3 = new OrderDto("ex@example.com", "제주도", "421521");

        List<OrderDto> orderList = List.of(dto1, dto2, dto3);

        when(orderRepository.findByEmail("ex@example.com")).thenReturn(orderList);

        // when
        List<OrderDto> orders = orderService.findAllOrdersByEmail("ex@example.com");

        // then
        assertThat(orders.size()).isEqualTo(3);
        assertThat(orders.get(0)).isEqualTo(dto1);
        assertThat(orders.get(1)).isEqualTo(dto2);
        assertThat(orders.get(2)).isEqualTo(dto3);
    }

}