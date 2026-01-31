package de.emaarco.example.application.service;

import de.emaarco.example.application.port.out.NewsletterSubscriptionRepository;
import de.emaarco.example.application.port.out.ProcessedOperationRepository;
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
 * Unit test for SendConfirmationMailService in idempotency-pattern.
 * Tests idempotency check and confirmation mail sending logic.
 */
@ExtendWith(MockitoExtension.class)
class SendConfirmationMailServiceTest {

    @Mock
    private NewsletterSubscriptionRepository repository;

    @Mock
    private ProcessedOperationRepository processedOperationRepository;

    @InjectMocks
    private SendConfirmationMailService underTest;

    @Test
    void shouldSendConfirmationMailWhenOperationIsNotProcessedYet() {
        // Given
        var subscriptionId = new SubscriptionId(UUID.randomUUID());
        var operationId = new OperationId(subscriptionId.value() + "-Activity_SendConfirmationMail");
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
        doNothing().when(processedOperationRepository).save(operationId);

        // When
        underTest.sendConfirmationMail(subscriptionId, operationId);

        // Then
        verify(processedOperationRepository, times(1)).existsById(operationId);
        verify(repository, times(1)).find(subscriptionId);
        verify(processedOperationRepository, times(1)).save(operationId);
        verifyNoMoreInteractions(repository, processedOperationRepository);
    }

    @Test
    void shouldSkipSendingConfirmationMailWhenOperationIsAlreadyProcessed() {
        // Given
        var subscriptionId = new SubscriptionId(UUID.randomUUID());
        var operationId = new OperationId(subscriptionId.value() + "-Activity_SendConfirmationMail");

        when(processedOperationRepository.existsById(operationId)).thenReturn(true);

        // When
        underTest.sendConfirmationMail(subscriptionId, operationId);

        // Then
        verify(processedOperationRepository, times(1)).existsById(operationId);
        verify(repository, times(0)).find(any());
        verify(processedOperationRepository, times(0)).save(any());
        verifyNoMoreInteractions(repository, processedOperationRepository);
    }
}
