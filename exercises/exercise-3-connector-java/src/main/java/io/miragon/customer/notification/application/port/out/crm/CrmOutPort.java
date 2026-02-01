package io.miragon.customer.notification.application.port.out.crm;

import io.miragon.customer.notification.domain.CustomerPreference;

public interface CrmOutPort {
    CustomerPreference fetchCustomerPreferences(String customerNo);
}
