package io.miragon.customer.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerPreference {
    private final String customerNo;
    private final String customerName;
    private final String email;
    private final String phone;
    private final NotificationChannel preferredChannel;
}
