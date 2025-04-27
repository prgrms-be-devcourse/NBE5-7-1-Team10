package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dto.OrderSummaryDto;
import io.sleepyhoon.project1.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailTemplateService {

    private final TemplateEngine thymeleaf;


    public String buildOrderConfirmHtml(Order order) {
        Context ctx = new Context();
        ctx.setVariable("order", order);
        return thymeleaf.process("order-confirm", ctx);
    }


    public String buildDailySummaryHtml(List<OrderSummaryDto> list) {
        Context ctx = new Context();
        ctx.setVariable("orders", list);
        ctx.setVariable("total",
                list.stream().mapToInt(OrderSummaryDto::totalPrice).sum());
        return thymeleaf.process("order-summary", ctx);
    }
}
