package io.sleepyhoon.project1.service;


import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.entity.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderMailService {

    private final OrderRepository orderRepository;
    private final JavaMailSender mailSender;
    private final MailTemplateService templateService;

    // 1. 주문 1건에 대해 완료 메일 전송
    public void sendOrderConfirmation(Order order) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        helper.setTo(order.getEmail());
        helper.setSubject("[싱글벙글 카페] 주문이 완료되었습니다 – #" + order.getId());
        helper.setText(templateService.buildOrderConfirmHtml(order), true);

        mailSender.send(message);
        log.info("주문 확인 메일 전송 완료 – email: {}, orderId: {}", order.getEmail(), order.getId());
    }

    // 2. 이메일 1건에 대해 모든 주문을 모아서 전송
    public void sendDailyOrderSummary(String email) throws MessagingException {
        List<Order> orders = orderRepository.findByEmail(email);

        if (orders.isEmpty()) {
            log.info("이메일 {}에 해당하는 주문 없음. 메일 전송 생략", email);
            return;
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

        helper.setTo(email);
        helper.setSubject("[싱글벙글 카페] 오늘의 주문 내역 안내");
        helper.setText(templateService.buildDailySummaryHtml(orders), true);

        mailSender.send(message);
        log.info("요약 주문 메일 전송 완료 – email: {}, 주문 건수: {}", email, orders.size());
    }
}
