package io.miragon.customer.notification.application.port.in;

public interface SendNotificationUseCase {
    NotificationResult sendNotification(SendNotificationCommand command);
}
