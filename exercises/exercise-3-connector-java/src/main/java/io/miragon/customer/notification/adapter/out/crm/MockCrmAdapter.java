package io.miragon.customer.notification.adapter.out.crm;

import io.miragon.customer.notification.application.port.out.crm.CrmOutPort;
import io.miragon.customer.notification.domain.CustomerPreference;
import io.miragon.customer.notification.domain.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class MockCrmAdapter implements CrmOutPort {

    // Simulated CRM database
    private static final Map<String, CustomerPreference> MOCK_CUSTOMERS = Map.of(
            "C001", new CustomerPreference("C001", "John Doe", "john.doe@example.com", "+1234567890", NotificationChannel.EMAIL),
            "C002", new CustomerPreference("C002", "Jane Smith", "jane.smith@example.com", "+0987654321", NotificationChannel.SMS),
            "C003", new CustomerPreference("C003", "Bob Wilson", "bob.wilson@example.com", "+1122334455", NotificationChannel.PUSH)
    );

    @Override
    public CustomerPreference fetchCustomerPreferences(String customerNo) {
        log.info("[MOCK CRM] Fetching preferences for customer: {}", customerNo);

        CustomerPreference preferences = MOCK_CUSTOMERS.get(customerNo);

        if (preferences == null) {
            // Return default preferences for unknown customers
            log.info("[MOCK CRM] Customer {} not found, returning default preferences", customerNo);
            return new CustomerPreference(
                    customerNo,
                    "Unknown Customer",
                    "default@example.com",
                    "+0000000000",
                    NotificationChannel.EMAIL
            );
        }

        log.info("[MOCK CRM] Found customer: {} with preferred channel: {}",
                preferences.getCustomerName(), preferences.getPreferredChannel());
        return preferences;
    }
}
