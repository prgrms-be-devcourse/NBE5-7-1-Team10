package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.OrderRepository;
import io.sleepyhoon.project1.dto.OrderSummaryDto;
import io.sleepyhoon.project1.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailySummaryTransactionService {

    private final OrderMailService orderMailService;
    private final OrderRepository orderRepository;

    //개별 이메일에 대한 메일 전송 + isProcessed 업데이트를 하나의 트랜잭션으로 수행

    @Transactional
    public void sendMailAndMarkProcessed(String email, List<OrderSummaryDto> summaries) throws Exception {
        orderMailService.sendDailyOrderSummary(email, summaries);

        if(true) {
            throw new Exception();
        }
        // 메일이 성공적으로 전송된 경우, 해당 주문들의 isProcessed를 true로 변경
        List<Long> ids = summaries.stream()
                .map(OrderSummaryDto::id)
                .toList();

        int updated = orderRepository.markProcessedTrueByIdIn(ids);
        log.info("isProcessed=True 업데이트 완료 – email={}, {}건", email, updated);
    }
}
