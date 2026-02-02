package io.miragon.example.adapter.out.memory;

import io.miragon.example.application.port.out.SubscriptionCounterRepository;
import io.miragon.example.domain.SubscriptionCounter;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemorySubscriptionCounterRepository implements SubscriptionCounterRepository {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public SubscriptionCounter find() {
        return new SubscriptionCounter(counter.get());
    }

    @Override
    public void save(SubscriptionCounter counter) {
        this.counter.set(counter.count());
    }
}
