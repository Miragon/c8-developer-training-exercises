package io.miragon.customer.notification.application.service;

import io.miragon.customer.notification.application.port.in.NotificationResult;
import io.miragon.customer.notification.application.port.in.SendNotificationCommand;
import io.miragon.customer.notification.application.port.in.SendNotificationUseCase;
import io.miragon.customer.notification.application.port.out.crm.CrmOutPort;
import io.miragon.customer.notification.application.port.out.notification.NotificationOutPort;
import io.miragon.customer.notification.domain.CustomerPreference;
import io.miragon.customer.notification.domain.NotificationChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendNotificationService implements SendNotificationUseCase {

    private final CrmOutPort crmOutPort;
    private final NotificationOutPort notificationOutPort;

    @Override
    public NotificationResult sendNotification(SendNotificationCommand command) {
        log.info("Processing notification for customer: {}", command.getCustomerNo());

        // Step 1: Fetch preferences from CRM
        CustomerPreference preferences = crmOutPort.fetchCustomerPreferences(command.getCustomerNo());
        log.info("Fetched preferences for customer {}: preferred channel = {}",
                preferences.getCustomerName(), preferences.getPreferredChannel());

        // Step 2: Select channel based on preferences
        NotificationChannel selectedChannel = selectChannel(preferences);
        log.info("Selected notification channel: {}", selectedChannel);

        // Step 3: Send notification
        boolean success = notificationOutPort.send(preferences, selectedChannel, command.getMessage());
        log.info("Notification sent: success = {}", success);

        return new NotificationResult(
                success,
                command.getCustomerNo(),
                preferences.getCustomerName(),
                selectedChannel,
                command.getMessage()
        );
    }

    private NotificationChannel selectChannel(CustomerPreference preferences) {
        // Use customer's preferred channel
        return preferences.getPreferredChannel();
    }
}
