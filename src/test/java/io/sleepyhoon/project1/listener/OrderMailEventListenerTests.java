//package io.sleepyhoon.project1.listener;
//
//import io.sleepyhoon.project1.entity.Order;
//import io.sleepyhoon.project1.event.OrderCreatedEvent;
//import io.sleepyhoon.project1.service.OrderMailService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.lang.reflect.Field;
//
//import static org.mockito.Mockito.*;
//
//@Slf4j
//@ExtendWith(MockitoExtension.class)
//class OrderMailEventListenerTests {
//
//    @InjectMocks
//    OrderMailEventListener eventListener;
//
//    @Mock
//    OrderMailService mailService;
//
//    @Test
//    @DisplayName("OrderCreatedEvent 주문 확인 메일 전송 시도")
//    void onOrderCreated_callsSendOrderConfirmation() throws Exception {
//        log.info("OrderCreatedEvent 테스트");
//
//
//
//        // given
//        Order fakeOrder = Order.builder()
//                .email("test@example.com")
//                .address("서울")
//                .postNum("12345")
//                .price(5000)
//                .build();
//
//
//        Field idField = Order.class.getDeclaredField("id");
//        idField.setAccessible(true);
//        idField.set(fakeOrder, 1L);
//
//        OrderCreatedEvent event = new OrderCreatedEvent(fakeOrder);
//
//        log.info(" 이벤트 객체 생성 완료: orderId={}, email={}", fakeOrder.getId(), fakeOrder.getEmail());
//
//        // when
//        eventListener.onOrderCreated(event);
//
//        log.info("이벤트 리스너 실행 완료");
//
//        // then
//        verify(mailService, times(1)).sendOrderConfirmation(fakeOrder);
//        log.info("메일 서비스 호출 확인 완료");
//    }
//}
