package io.miragon.example.adapter.out.db.subscription;

import io.miragon.example.domain.SubscriptionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "newsletter_subscription")
public class NewsletterSubscriptionEntity {

    @Id
    @Column(name = "subscription_id", nullable = false)
    private UUID subscriptionId;

    @Column(name = "subscriber_name", nullable = false)
    private String name;

    @Column(name = "subscriber_mail", nullable = false)
    private String email;

    @Column(name = "newsletter_id", nullable = false)
    private UUID newsletterId;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status", nullable = false)
    private SubscriptionStatus status;

    public NewsletterSubscriptionEntity() {
    }

    public NewsletterSubscriptionEntity(
            UUID subscriptionId,
            String name,
            String email,
            UUID newsletterId,
            LocalDateTime registrationDate,
            SubscriptionStatus status
    ) {
        this.subscriptionId = subscriptionId;
        this.name = name;
        this.email = email;
        this.newsletterId = newsletterId;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public UUID getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(UUID subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getNewsletterId() {
        return newsletterId;
    }

    public void setNewsletterId(UUID newsletterId) {
        this.newsletterId = newsletterId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }
}
