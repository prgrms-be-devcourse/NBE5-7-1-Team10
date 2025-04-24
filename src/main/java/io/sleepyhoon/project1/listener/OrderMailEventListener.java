package io.sleepyhoon.project1.listener;

import io.sleepyhoon.project1.event.OrderCreatedEvent;
import io.sleepyhoon.project1.service.OrderMailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMailEventListener {

    private final OrderMailService mailService;


    @Async
    @TransactionalEventListener(
            classes = OrderCreatedEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void onOrderCreated(OrderCreatedEvent event) {

        try {
            mailService.sendOrderConfirmation(event.order());
        } catch (MessagingException e) {
            log.error("주문 확인 메일 발송 실패 – orderId={}, 에러={}",
                    event.order().getId(), e.getMessage(), e);
        }
    }
}