package io.miragon.customer.notification.application.port.in;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationCommand {

    @TemplateProperty(
            label = "Customer Number",
            description = "The unique customer identifier to look up preferences"
    )
    private String customerNo;

    @TemplateProperty(
            label = "Message",
            description = "The notification message to send"
    )
    private String message;
}
