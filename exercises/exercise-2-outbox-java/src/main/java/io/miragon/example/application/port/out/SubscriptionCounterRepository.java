package io.miragon.example.application.port.out;

import io.miragon.example.domain.SubscriptionCounter;

public interface SubscriptionCounterRepository {
    SubscriptionCounter find();
    void save(SubscriptionCounter counter);
}
