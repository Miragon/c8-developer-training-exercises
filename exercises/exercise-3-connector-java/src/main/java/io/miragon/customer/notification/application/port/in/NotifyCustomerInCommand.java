package io.miragon.customer.notification.application.port.in;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.DropdownPropertyChoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class NotifyCustomerInCommand {

    @TemplateProperty(
            label = "Notification Method",
            description = "Select how to notify the customer",
            type = TemplateProperty.PropertyType.Dropdown,
            choices = {
                    @DropdownPropertyChoice(label = "Email", value = "EMAIL"),
                    @DropdownPropertyChoice(label = "SMS", value = "SMS")
            }
    )
    private String notificationMethod;

    private String mailTopic;
    private String message;
    private String customerName;
    private String customerMobilePhone;
    private String customerEMail;
}
