package de.emaarco.example.application.port.inbound.newsletter;

import de.emaarco.example.domain.Email;
import de.emaarco.example.domain.Name;
import de.emaarco.example.domain.NewsletterId;
import de.emaarco.example.domain.SubscriptionId;

public interface SubscribeToNewsletterUseCase {

    record Command(
        Email email,
        Name name,
        NewsletterId newsletterId
    ) {}

    SubscriptionId subscribe(Command command);
}
