package io.miragon.example.application.port.in;

import io.miragon.example.domain.Email;
import io.miragon.example.domain.Name;
import io.miragon.example.domain.NewsletterId;
import io.miragon.example.domain.SubscriptionId;

public interface SubscribeToNewsletterUseCase {

    SubscriptionId subscribe(Command command);

    record Command(
            Email email,
            Name name,
            NewsletterId newsletterId
    ) {
    }
}
