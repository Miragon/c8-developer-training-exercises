package io.miragon.example.domain;

public record SubscriptionCounter(int count) {
    public SubscriptionCounter increment() {
        return new SubscriptionCounter(count + 1);
    }
}
