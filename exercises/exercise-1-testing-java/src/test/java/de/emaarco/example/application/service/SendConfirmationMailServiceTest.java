package de.emaarco.example.application.service;

import de.emaarco.example.application.port.outbound.newsletter.NewsletterSubscriptionRepository;
import de.emaarco.example.application.service.newsletter.SendConfirmationMailService;
import de.emaarco.example.domain.NewsletterSubscription;
import de.emaarco.example.domain.SubscriptionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static de.emaarco.example.domain.TestObjectBuilder.testNewsletterSubscription;
import static org.mockito.Mockito.*;

class SendConfirmationMailServiceTest {

    private NewsletterSubscriptionRepository subscriptionRepository;
    private SendConfirmationMailService underTest;

    @BeforeEach
    void setUp() {
        subscriptionRepository = mock(NewsletterSubscriptionRepository.class);
        underTest = new SendConfirmationMailService(subscriptionRepository);
    }

    @Test
    void sendConfirmationMail() {
        // Given
        SubscriptionId subscriptionId = new SubscriptionId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        NewsletterSubscription subscription = testNewsletterSubscription(subscriptionId);

        when(subscriptionRepository.find(subscriptionId)).thenReturn(subscription);

        // When
        underTest.sendConfirmationMail(subscriptionId);

        // Then
        verify(subscriptionRepository).find(subscriptionId);
        verifyNoMoreInteractions(subscriptionRepository);
    }
}
