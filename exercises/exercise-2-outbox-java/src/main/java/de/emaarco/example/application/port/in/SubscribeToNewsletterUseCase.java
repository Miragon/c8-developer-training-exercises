package de.emaarco.example.application.port.in;

import de.emaarco.example.domain.Email;
import de.emaarco.example.domain.Name;
import de.emaarco.example.domain.NewsletterId;
import de.emaarco.example.domain.SubscriptionId;

public interface SubscribeToNewsletterUseCase {

    SubscriptionId subscribe(Command command);

    record Command(
            Email email,
            Name name,
            NewsletterId newsletterId
    ) {
    }
}
