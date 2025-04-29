package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dto.OrderSummaryDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailRetryService {

    private final MailRetryQueue retryQueue;
    private final OrderMailService orderMailService;
    private final DailySummaryTransactionService transactionService;



    // 30분마다 실패한 메일 재시도 (최대 1회)
    @Scheduled(cron = "0 */2 * * * *", zone = "Asia/Seoul")
    public void retryFailedMails() {
        Map<String, MailRetryQueue.FailedMail> failed = new HashMap<>(retryQueue.getFailedEmails());

        for (Map.Entry<String, MailRetryQueue.FailedMail> entry : failed.entrySet()) {
            String email = entry.getKey();
            MailRetryQueue.FailedMail failedMail = entry.getValue();
            List<OrderSummaryDto> summaries = failedMail.getSummaries();

            // 최대 재시도 1회
            if (failedMail.getRetryCount() >= 2) {
                log.warn("최대 재시도 초과 – 관리자에게 알림 예정, email={}", email);
                notifyAdmin(email, summaries);
                continue;
            }

            try {
                transactionService.sendMailAndMarkProcessed(email, summaries);
                log.info("재시도 메일 전송 성공 – email={}", email);
                retryQueue.getFailedEmails().remove(email);
            } catch (Exception e) {
                log.error("재시도 메일 전송 실패 – email={}, error={}", email, e.getMessage(), e);
                retryQueue.addFailedEmail(email, summaries);
            }
        }
    }

    //재시도까지 실패한 경우 관리자에게 보고 메일 전송
     // 관리자 메일 환경변수로 설정한 송신자 메일이랑 같음
    private void notifyAdmin(String failedEmail, List<OrderSummaryDto> summaries) {
        try {
            orderMailService.sendFailureReportToAdmin(failedEmail, summaries);
            log.info("관리자 알림 전송 성공 – 대상: {}", failedEmail);
            retryQueue.getFailedEmails().remove(failedEmail);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("관리자 알림 전송 실패 – 대상: {}, error={} ", failedEmail, e.getMessage(), e);
        }
    }
}