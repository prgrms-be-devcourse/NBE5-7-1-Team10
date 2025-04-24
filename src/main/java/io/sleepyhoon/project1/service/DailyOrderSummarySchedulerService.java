package io.sleepyhoon.project1.service;


import io.sleepyhoon.project1.dao.OrderRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyOrderSummarySchedulerService {
    private final OrderRepository orderRepository;
    private final OrderMailService orderMailService;
    public void sendAllDailySummaries() {
        List<String> emails = orderRepository.findDistinctEmails();
        log.info("📬 요약 메일 대상 이메일 수: {}", emails.size());

        for (String email : emails) {
            try {
                orderMailService.sendDailyOrderSummary(email);
            } catch (MessagingException e) {
                log.error(" 메일 전송 실패 – email: {}, 오류: {}", email, e.getMessage(), e);
            }
        }
    }
}
