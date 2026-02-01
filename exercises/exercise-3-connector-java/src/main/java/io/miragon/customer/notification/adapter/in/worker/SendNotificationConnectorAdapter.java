package io.miragon.customer.notification.adapter.in.worker;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.generator.java.annotation.ElementTemplate;
import io.miragon.customer.notification.application.port.in.NotificationResult;
import io.miragon.customer.notification.application.port.in.SendNotificationCommand;
import io.miragon.customer.notification.application.port.in.SendNotificationUseCase;
import lombok.Setter;

@Setter
@OutboundConnector(
        name = "Send Notification Connector",
        inputVariables = {"customerNo", "message"},
        type = "sendNotification"
)
@ElementTemplate(
        id = "sendNotification",
        name = "Send Notification Connector",
        inputDataClass = SendNotificationCommand.class,
        version = 1,
        description = "Fetches customer preferences from CRM, selects the preferred channel, and sends a notification.",
        propertyGroups = {
                @ElementTemplate.PropertyGroup(id = "customerNo", label = "Customer Number"),
                @ElementTemplate.PropertyGroup(id = "message", label = "Message")
        }
)
public class SendNotificationConnectorAdapter implements OutboundConnectorFunction {

    private SendNotificationUseCase sendNotificationUseCase;

    @Override
    public Object execute(OutboundConnectorContext context) {
        final SendNotificationCommand command = context.bindVariables(SendNotificationCommand.class);
        NotificationResult result = sendNotificationUseCase.sendNotification(command);
        return result;
    }
}
