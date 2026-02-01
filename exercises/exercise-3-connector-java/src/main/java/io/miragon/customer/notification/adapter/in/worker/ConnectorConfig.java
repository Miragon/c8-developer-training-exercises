package io.miragon.customer.notification.adapter.in.worker;

import io.miragon.customer.notification.application.port.in.SendNotificationUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorConfig {

    @Bean
    @ConditionalOnBean(SendNotificationUseCase.class)
    public SendNotificationConnectorAdapter sendNotificationConnectorAdapter(SendNotificationUseCase sendNotificationUseCase) {
        SendNotificationConnectorAdapter adapter = new SendNotificationConnectorAdapter();
        adapter.setSendNotificationUseCase(sendNotificationUseCase);
        return adapter;
    }
}

