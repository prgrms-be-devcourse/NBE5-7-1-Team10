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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyOrderSummarySchedulerService {

    private final OrderRepository orderRepository;
    private final OrderMailService orderMailService;


    @Scheduled(cron = "0 0 14 * * *", zone = "Asia/Seoul")
    @Transactional
    public void sendAllDailySummaries() {

        ZoneId zone = ZoneId.of("Asia/Seoul");
        LocalDate today = LocalDate.now(zone);
        LocalDateTime start = today.atTime(2, 0);
        LocalDateTime end   = today.atTime(14, 0);


        List<Order> orders =
                orderRepository.findByIsProcessedFalseAndOrderedAtBetween(start, end);

        if (orders.isEmpty()) {
            log.info("오늘 02-14시 주문 없음 → 메일 발송 생략");
            return;
        }


        Map<String, List<OrderSummaryDto>> map =
                orders.stream()
                        .map(OrderSummaryDto::from)
                        .collect(Collectors.groupingBy(OrderSummaryDto::email));


        map.forEach((email, summaries) -> {
            try {
                orderMailService.sendDailyOrderSummary(email, summaries);
            } catch (MessagingException e) {
                log.error("전체주문내역 메일 전송  실패 – email={}, error={}", email, e.getMessage(), e);
            }
        });

        // isProcessed=True로 일괄 업데이트
        List<Long> ids = orders.stream()
                .map(Order::getId)
                .toList();

        int updated = orderRepository.markProcessedTrueByIdIn(ids);
        log.info("isProcessed=True 업데이트 {}건 완료", updated);
    }
}
