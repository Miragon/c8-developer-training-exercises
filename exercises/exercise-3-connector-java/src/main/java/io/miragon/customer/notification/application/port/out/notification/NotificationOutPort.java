package io.miragon.customer.notification.application.port.out.notification;

import io.miragon.customer.notification.domain.CustomerPreference;
import io.miragon.customer.notification.domain.NotificationChannel;

public interface NotificationOutPort {
    boolean send(CustomerPreference customer, NotificationChannel channel, String message);
}
