package de.emaarco.example.adapter.out.memory;

import de.emaarco.example.application.port.out.SubscriptionCounterRepository;
import de.emaarco.example.domain.SubscriptionCounter;
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
