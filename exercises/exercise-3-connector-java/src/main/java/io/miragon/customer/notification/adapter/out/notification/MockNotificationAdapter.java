package io.miragon.customer.notification.adapter.out.notification;

import io.miragon.customer.notification.application.port.out.notification.NotificationOutPort;
import io.miragon.customer.notification.domain.CustomerPreference;
import io.miragon.customer.notification.domain.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MockNotificationAdapter implements NotificationOutPort {

    @Override
    public boolean send(CustomerPreference customer, NotificationChannel channel, String message) {
        log.info("[MOCK NOTIFICATION] Sending notification via {}", channel);

        switch (channel) {
            case EMAIL -> sendEmail(customer, message);
            case SMS -> sendSms(customer, message);
            case PUSH -> sendPush(customer, message);
        }

        log.info("[MOCK NOTIFICATION] Notification sent successfully");
        return true;
    }

    private void sendEmail(CustomerPreference customer, String message) {
        log.info("[MOCK EMAIL] To: {} <{}>", customer.getCustomerName(), customer.getEmail());
        log.info("[MOCK EMAIL] Subject: Notification");
        log.info("[MOCK EMAIL] Body: {}", message);
    }

    private void sendSms(CustomerPreference customer, String message) {
        log.info("[MOCK SMS] To: {} ({})", customer.getCustomerName(), customer.getPhone());
        log.info("[MOCK SMS] Message: {}", message);
    }

    private void sendPush(CustomerPreference customer, String message) {
        log.info("[MOCK PUSH] To: {} (Customer ID: {})", customer.getCustomerName(), customer.getCustomerNo());
        log.info("[MOCK PUSH] Notification: {}", message);
    }
}
