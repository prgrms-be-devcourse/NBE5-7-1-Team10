package io.sleepyhoon.project1.service;


import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.OrderSummaryDto;
import io.sleepyhoon.project1.entity.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderMailService {

    private final JavaMailSender mailSender;
    private final MailTemplateService templateService;

    @Value("${spring.mail.from.address}")
    private String fromAddress;

    @Value("${spring.mail.from.name}")
    private String fromName;



    public void sendOrderConfirmation(Order order) throws MessagingException, UnsupportedEncodingException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                msg, true, StandardCharsets.UTF_8.name());

        helper.setFrom(fromAddress, fromName);
        helper.setTo(order.getMember().getEmail());

        helper.setSubject("[싱글벙글 카페] 주문이 완료되었습니다 – #" + order.getId());
        helper.setText(templateService.buildOrderConfirmHtml(order), true);

        mailSender.send(msg);
        log.info("주문 확인 메일 전송 완료 – orderId={}, email={}",
                order.getId(), order.getMember().getEmail());
    }


    public void sendDailyOrderSummary(String email,
                                  List<OrderSummaryDto> summaries) throws  MessagingException, UnsupportedEncodingException, Exception {
        if (summaries.isEmpty()) return;

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper =
                new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());

        helper.setFrom(fromAddress, fromName);
        helper.setTo(email);
        helper.setSubject("[싱글벙글 카페] 오늘 주문 요약");
        helper.setText(templateService.buildDailySummaryHtml(summaries), true);

        mailSender.send(msg);
        log.info("요약 메일 전송 완료 – email={}, 건수={}",
                email, summaries.size());

    }
    public void sendFailureReportToAdmin(String failedEmail, List<OrderSummaryDto> summaries) throws MessagingException, UnsupportedEncodingException {
        // 관리자에게 보낼 이메일 내용 설정
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());

        helper.setFrom(fromAddress, fromName);
        helper.setTo(fromName);
        helper.setSubject("[싱글벙글 카페] 실패한 메일 알림 - " + failedEmail);
        helper.setText(templateService.buildFailureReportHtml(failedEmail, summaries), true); // 실패한 이메일과 요약 정보를 포함

        mailSender.send(msg);
        log.info("관리자에게 실패한 메일 보고 – 대상: {}", failedEmail);
    }


}