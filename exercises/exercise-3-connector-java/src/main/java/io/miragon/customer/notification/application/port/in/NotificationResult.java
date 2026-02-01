package io.miragon.customer.notification.application.port.in;

import io.miragon.customer.notification.domain.NotificationChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResult {
    private final boolean success;
    private final String customerNo;
    private final String customerName;
    private final NotificationChannel channelUsed;
    private final String message;
}
