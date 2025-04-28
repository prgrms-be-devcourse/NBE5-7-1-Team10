package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.entity.Order;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderMailServiceTests {

    @InjectMocks
    OrderMailService mailService;

    @Mock
    JavaMailSender mailSender;

    @Mock
    MailTemplateService templateService;

    @Mock
    MimeMessage mimeMessage;

    @BeforeEach
    void setUp() {
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    @DisplayName("주문 확인 메일 전송 - 메일 작성 및 발송")
    void sendOrderConfirmation_sendsEmail() throws Exception {
        log.info("주문 확인 메일 전송 테스트");

        // given
        Order order = Order.builder()
                .email("test@example.com")
                .address("서울")
                .postNum("12345")
                .price(5000)
                .build();


        Field idField = Order.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(order, 1L);

        when(templateService.buildOrderConfirmHtml(order)).thenReturn("<html>테스트 본문</html>");

        log.info("주문 객체 생성 완료: orderId={}, email={}", order.getId(), order.getEmail());

        // when
        mailService.sendOrderConfirmation(order);

        log.info("메일 전송 메서드 실행 완료");

        // then
        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(mimeMessage);
        log.info("메일 생성 및 전송 호출 검증 완료");
    }
}
