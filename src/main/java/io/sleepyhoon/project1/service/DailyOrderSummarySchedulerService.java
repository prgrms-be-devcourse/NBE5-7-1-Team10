package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.OrderSummaryDto;
import io.sleepyhoon.project1.entity.Order;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyOrderSummarySchedulerService {

    private final OrderRepository orderRepository;
    private final OrderMailService orderMailService;
    private final DailySummaryTransactionService transactionService;
    private final MailRetryQueue mailRetryQueue;

    //14 시에 메일 전송하도록 스케쥴링 설정
    @Scheduled(cron = "0 50 7 * * *", zone = "Asia/Seoul")
    @Transactional
    public void sendAllDailySummaries() {
        ZoneId zone = ZoneId.of("Asia/Seoul");
        LocalDateTime start = LocalDate.now(zone).minusDays(1).atTime(14, 0);
        LocalDateTime end = LocalDate.now(zone).atTime(14, 0);

        List<Order> orders = orderRepository.findByIsProcessedFalseAndOrderedAtBetween(start, end);
        if (orders.isEmpty()) {
            log.info("전날 14시-오늘 14시 사이 주문 없음, 메일 발송 생략");
            return;
        }

        // 이메일별로 주문 요약 분류
        Map<String, List<OrderSummaryDto>> map =
                orders.stream()
                        .map(OrderSummaryDto::from)
                        .collect(Collectors.groupingBy(OrderSummaryDto::email));


        for (Map.Entry<String, List<OrderSummaryDto>> entry : map.entrySet()) {
            String email = entry.getKey();
            List<OrderSummaryDto> summaries = entry.getValue();

            try {
                // 메일 전송 + 성공 시 DB 업데이트는 별도 트랜잭션으로 실행
                transactionService.sendMailAndMarkProcessed(email, summaries);
                log.info("메일 전송 성공 – email={}", email);
            } catch (Exception e) {
                // 실패한 메일은 로그 + 큐에 저장
                log.error("메일 전송 실패 – email={}, error={}", email, e.getMessage(), e);
                mailRetryQueue.addFailedEmail(email, summaries);
            }
        }
    }
}
