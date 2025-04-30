package io.sleepyhoon.project1.service;
import io.sleepyhoon.project1.dto.OrderSummaryDto;
import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class MailRetryQueue {

    // 실패한 이메일 저장용 Map: email -> 실패 정보 표시
    private final Map<String, FailedMail> failedEmails = new HashMap<>();

    // 실패한 메일 추가 시 기존에 있던 메일이면 retryCounnt를 증가

    public void addFailedEmail(String email, List<OrderSummaryDto> summaries) {
        failedEmails.merge(email,
                new FailedMail(summaries, 1),
                (oldVal, newVal) -> {
                    oldVal.incrementRetry();
                    return oldVal;
                });
        log.warn("메일 재시도 큐에 추가됨 – email={}, {}건", email, summaries.size());
    }

    public Map<String, FailedMail> getFailedEmails() {
        return failedEmails;
    }

    public void clear() {
        failedEmails.clear();
    }

    //재시도 하는  내부 클래스: 요약 + 재시도 횟수
    @Getter
    public static class FailedMail {
        private final List<OrderSummaryDto> summaries;
        private int retryCount;

        public FailedMail(List<OrderSummaryDto> summaries, int retryCount) {
            this.summaries = summaries;
            this.retryCount = retryCount;
        }

        public void incrementRetry() {
            this.retryCount++;
        }
    }
}
