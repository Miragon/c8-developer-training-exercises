package de.emaarco.example.adapter.out.db.subscription;

import de.emaarco.example.application.port.out.NewsletterSubscriptionRepository;
import de.emaarco.example.domain.NewsletterSubscription;
import de.emaarco.example.domain.SubscriptionId;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class NewsletterSubscriptionPersistenceAdapter implements NewsletterSubscriptionRepository {

    private final NewsletterSubscriptionJpaRepository repository;

    public NewsletterSubscriptionPersistenceAdapter(NewsletterSubscriptionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public NewsletterSubscription find(SubscriptionId subscriptionId) {
        var entity = repository.findBySubscriptionId(subscriptionId.value());
        if (entity == null) {
            throw new NoSuchElementException();
        }
        return NewsletterSubscriptionEntityMapper.toDomain(entity);
    }

    @Override
    public NewsletterSubscription search(SubscriptionId subscriptionId) {
        var entity = repository.findBySubscriptionId(subscriptionId.value());
        if (entity == null) {
            return null;
        }
        return NewsletterSubscriptionEntityMapper.toDomain(entity);
    }

    @Override
    public void save(NewsletterSubscription subscription) {
        var entity = NewsletterSubscriptionEntityMapper.toEntity(subscription);
        repository.save(entity);
    }

    @Override
    public void delete(SubscriptionId subscriptionId) {
        repository.deleteById(subscriptionId.value());
    }
}
