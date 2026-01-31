package de.emaarco.example.application.port.out;

import de.emaarco.example.domain.SubscriptionCounter;

public interface SubscriptionCounterRepository {
    SubscriptionCounter find();
    void save(SubscriptionCounter counter);
}
