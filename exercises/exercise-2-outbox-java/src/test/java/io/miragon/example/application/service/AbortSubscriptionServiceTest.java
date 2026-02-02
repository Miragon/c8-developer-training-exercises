package io.miragon.example.application.service;

import io.miragon.example.application.port.out.NewsletterSubscriptionRepository;
import io.miragon.example.application.port.out.ProcessedOperationRepository;
import io.miragon.example.domain.*;
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
 * Unit test for AbortSubscriptionService in idempotency-pattern.
 * Tests idempotency check and subscription abort logic.
 */
@ExtendWith(MockitoExtension.class)
class AbortSubscriptionServiceTest {

    @Mock
    private NewsletterSubscriptionRepository repository;

    @Mock
    private ProcessedOperationRepository processedOperationRepository;

    @InjectMocks
    private AbortSubscriptionService underTest;

    @Test
    void shouldAbortSubscriptionWhenOperationIsNotProcessedYet() {
        // Given
        var subscriptionId = new SubscriptionId(UUID.randomUUID());
        var operationId = new OperationId(subscriptionId.value() + "-Activity_AbortRegistration");
        var subscription = new NewsletterSubscription(
                subscriptionId,
                new Name("Test User"),
                new Email("test@example.com"),
                new NewsletterId(UUID.randomUUID()),
                LocalDateTime.now(),
                SubscriptionStatus.PENDING
        );

        when(processedOperationRepository.existsById(operationId)).thenReturn(false);
        when(repository.find(subscriptionId)).thenReturn(subscription);
        doNothing().when(repository).save(any(NewsletterSubscription.class));
        doNothing().when(processedOperationRepository).save(operationId);

        // When
        underTest.abort(subscriptionId, operationId);

        // Then
        verify(processedOperationRepository, times(1)).existsById(operationId);
        verify(repository, times(1)).find(subscriptionId);
        verify(repository, times(1)).save(any());
        verify(processedOperationRepository, times(1)).save(operationId);
        verifyNoMoreInteractions(repository, processedOperationRepository);
    }

    @Test
    void shouldSkipAbortingSubscriptionWhenOperationIsAlreadyProcessed() {
        // Given
        var subscriptionId = new SubscriptionId(UUID.randomUUID());
        var operationId = new OperationId(subscriptionId.value() + "-Activity_AbortRegistration");

        when(processedOperationRepository.existsById(operationId)).thenReturn(true);

        // When
        underTest.abort(subscriptionId, operationId);

        // Then
        verify(processedOperationRepository, times(1)).existsById(operationId);
        verify(repository, times(0)).find(any());
        verify(repository, times(0)).save(any(NewsletterSubscription.class));
        verify(processedOperationRepository, times(0)).save(any());
        verifyNoMoreInteractions(repository, processedOperationRepository);
    }
}
