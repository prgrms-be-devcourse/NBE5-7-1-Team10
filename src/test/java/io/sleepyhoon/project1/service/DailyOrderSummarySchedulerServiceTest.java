//package io.sleepyhoon.project1.service;
//
//import io.sleepyhoon.project1.dao.OrderRepository;
//import io.sleepyhoon.project1.entity.Order;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.lang.reflect.Field;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//@Slf4j
//@ExtendWith(MockitoExtension.class)
//class DailyOrderSummarySchedulerServiceTests {
//
//    @InjectMocks
//    DailyOrderSummarySchedulerService schedulerService;
//
//    @Mock
//    OrderRepository orderRepository;
//
//    @Mock
//    OrderMailService orderMailService;
//
//    Order order1;
//    Order order2;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        order1 = Order.builder()
//                .email("test1@example.com")
//                .address("서울")
//                .postNum("12345")
//                .build();
//
//        order2 = Order.builder()
//                .email("test2@example.com")
//                .address("서울")
//                .postNum("67890")
//                .build();
//
//
//        Field idField1 = Order.class.getDeclaredField("id");
//        idField1.setAccessible(true);
//        idField1.set(order1, 1L);
//
//        Field idField2 = Order.class.getDeclaredField("id");
//        idField2.setAccessible(true);
//        idField2.set(order2, 2L);
//    }
//
//    @Test
//    @DisplayName("일일주문네역 메일 정상 발송")
//    void sendDailySummaryMailsTest() throws Exception {
//        // given
//        when(orderRepository.findByIsProcessedFalseAndOrderedAtBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
//                .thenReturn(List.of(order1, order2));
//
//        // when(스캐쥴러 사용하지 않고 직접 호출)
//        schedulerService.sendAllDailySummaries();
//
//        // then
//        verify(orderMailService, times(1)).sendDailyOrderSummary(eq("test1@example.com"), anyList());
//        verify(orderMailService, times(1)).sendDailyOrderSummary(eq("test2@example.com"), anyList());
//    }
//}