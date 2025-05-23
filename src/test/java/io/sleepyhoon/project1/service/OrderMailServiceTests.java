package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.entity.Member;
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
import org.springframework.test.util.ReflectionTestUtils;

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
        // mailSender가 MimeMessage를 반환하도록 설정
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // fromAddress, fromName 설정값 강제 주입
        ReflectionTestUtils.setField(mailService, "fromAddress", "test@example.com");
        ReflectionTestUtils.setField(mailService, "fromName", "Test User");
    }

    @Test
    @DisplayName("주문 확인 메일 전송 - 메일 작성 및 발송")
    void sendOrderConfirmation_sendsEmail() throws Exception {
        log.info("주문 확인 메일 전송 테스트 시작");

        // given
        Member member = Member.builder()
                .email("testuser@example.com")
                .build();

        Order order = Order.builder()
                .member(member)
                .address("서울특별시 강남구")
                .postNum("12345")
                .price(12000)
                .build();

        // Order 객체의 id 필드 강제 세팅
        setOrderId(order, 1L);

        when(templateService.buildOrderConfirmHtml(order)).thenReturn("<html><body>주문 확인</body></html>");

        log.info("주문 객체 및 템플릿 생성 완료: orderId={}, email={}", order.getId(), order.getMember().getEmail());

        // when
        mailService.sendOrderConfirmation(order);

        log.info("메일 전송 메서드 호출 완료");

        // then
        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(mimeMessage);
        log.info("메일 생성 및 전송 호출 검증 완료");
    }

    // Order의 id 필드를 리플렉션으로 세팅하는 헬퍼 메서드
    private void setOrderId(Order order, Long id) throws Exception {
        Field idField = Order.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(order, id);
    }
}
