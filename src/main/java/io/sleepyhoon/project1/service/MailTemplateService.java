package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
// 타임리프 템플릿 HTML 생성 서비스 (주문완료용, 당일전체용)

@Slf4j
@Service
@RequiredArgsConstructor
public class MailTemplateService {

    private final TemplateEngine templateEngine;

    // 주문 완료 시 사용되는 HTML 템플릿 생성
    public String buildOrderConfirmHtml(Order order) {
        Context ctx = new Context();
        ctx.setVariable("order", order);
        ctx.setVariable("items", order.getCoffeeOrders());
        String html = templateEngine.process("order-confirm", ctx);
        log.debug("📄 주문 확인 HTML 생성 완료 – orderId: {}", order.getId());
        return html;
    }

    // 하루 요약 메일용 HTML 템플릿 생성
    public String buildDailySummaryHtml(List<Order> orders) {
        Context ctx = new Context();
        ctx.setVariable("orders", orders);
        String html = templateEngine.process("order-summary", ctx);
        log.debug(" 주문 요약 HTML 생성 완료 – 주문 수: {}", orders.size());
        return html;
    }
}

