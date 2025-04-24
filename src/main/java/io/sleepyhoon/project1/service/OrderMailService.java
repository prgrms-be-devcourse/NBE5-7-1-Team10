package io.sleepyhoon.project1.service;


import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.OrderSummaryDto;
import io.sleepyhoon.project1.entity.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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

    private final JavaMailSender mailSender;
    private final MailTemplateService templateService;


    public void sendOrderConfirmation(Order order) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                msg, true, StandardCharsets.UTF_8.name());

        helper.setTo(order.getEmail());
        helper.setSubject("[싱글벙글 카페] 주문이 완료되었습니다 – #" + order.getId());
        helper.setText(templateService.buildOrderConfirmHtml(order), true);

        mailSender.send(msg);
        log.info("주문 확인 메일 전송 완료 – orderId={}, email={}",
                order.getId(), order.getEmail());
    }


    public void sendDailyOrderSummary(String email,
                                  List<OrderSummaryDto> summaries) throws MessagingException {
        if (summaries.isEmpty()) return;

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper =
                new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());

        helper.setTo(email);
        helper.setSubject("[싱글벙글 카페] 오늘 주문 요약");
        helper.setText(templateService.buildDailySummaryHtml(summaries), true);

        mailSender.send(msg);
        log.info("요약 메일 전송 완료 – email={}, 건수={}",
                email, summaries.size());

    }

}