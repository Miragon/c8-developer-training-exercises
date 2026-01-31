package de.emaarco.example.application.service;

import de.emaarco.example.application.port.out.NewsletterSubscriptionProcess;
import de.emaarco.example.application.port.out.NewsletterSubscriptionRepository;
import de.emaarco.example.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for ConfirmSubscriptionService in idempotency-pattern.
 * Tests subscription confirmation and process notification logic.
 */
@ExtendWith(MockitoExtension.class)
class ConfirmSubscriptionServiceTest {

    @Mock
    private NewsletterSubscriptionRepository repository;

    @Mock
    private NewsletterSubscriptionProcess processPort;

    @InjectMocks
    private ConfirmSubscriptionService underTest;

    @Test
    void shouldConfirmSubscriptionAndNotifyProcess() {
        // Given
        var subscriptionId = new SubscriptionId(UUID.randomUUID());
        var subscription = new NewsletterSubscription(
                subscriptionId,
                new Name("Test User"),
                new Email("test@example.com"),
                new NewsletterId(UUID.randomUUID()),
                LocalDateTime.now(),
                SubscriptionStatus.PENDING
        );

        when(repository.find(subscriptionId)).thenReturn(subscription);
        doNothing().when(repository).save(any(NewsletterSubscription.class));
        doNothing().when(processPort).confirmSubscription(subscriptionId);

        // When
        underTest.confirm(subscriptionId);

        // Then
        verify(repository, times(1)).find(subscriptionId);
        verify(repository, times(1)).save(any());
        verify(processPort, times(1)).confirmSubscription(subscriptionId);
        verifyNoMoreInteractions(repository, processPort);
    }
}
