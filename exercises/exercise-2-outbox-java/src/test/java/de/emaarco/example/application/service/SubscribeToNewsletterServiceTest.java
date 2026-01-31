package de.emaarco.example.application.service;

import de.emaarco.example.application.port.in.SubscribeToNewsletterUseCase;
import de.emaarco.example.application.port.out.NewsletterSubscriptionProcess;
import de.emaarco.example.application.port.out.NewsletterSubscriptionRepository;
import de.emaarco.example.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for SubscribeToNewsletterService in idempotency-pattern.
 * Tests subscription creation and process notification logic.
 */
@ExtendWith(MockitoExtension.class)
class SubscribeToNewsletterServiceTest {

    @Mock
    private NewsletterSubscriptionRepository repository;

    @Mock
    private NewsletterSubscriptionProcess processPort;

    @InjectMocks
    private SubscribeToNewsletterService underTest;

    @Test
    void shouldCreateSubscriptionAndNotifyProcessWhenSubscribingToNewsletter() {
        // Given
        var command = new SubscribeToNewsletterUseCase.Command(
                new Email("test@example.com"),
                new Name("Test User"),
                new NewsletterId(UUID.randomUUID())
        );

        doNothing().when(repository).save(any(NewsletterSubscription.class));
        doNothing().when(processPort).submitForm(any());

        // When
        var subscriptionId = underTest.subscribe(command);

        // Then
        verify(repository, times(1)).save(any());
        verify(processPort, times(1)).submitForm(subscriptionId);
        verifyNoMoreInteractions(repository, processPort);
    }
}
