package de.emaarco.example.adapter.in.zeebe;

import de.emaarco.example.application.port.in.SendConfirmationMailUseCase;
import de.emaarco.example.domain.OperationId;
import de.emaarco.example.domain.SubscriptionId;
import io.camunda.client.api.response.ActivatedJob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

/**
 * Unit test for SendConfirmationMailWorker in idempotency-pattern.
 * Tests that the worker correctly extracts variables, generates operation ID, and calls the use case.
 */
@ExtendWith(MockitoExtension.class)
class SendConfirmationMailWorkerTest {

    @Mock
    private SendConfirmationMailUseCase useCase;

    @InjectMocks
    private SendConfirmationMailWorker underTest;

    @Test
    void shouldSendConfirmationMailWithOperationIdWhenJobIsReceived() {
        // Given
        var subscriptionIdString = "123e4567-e89b-12d3-a456-426614174000";
        var elementId = "Activity_SendConfirmationMail";
        var subscriptionId = new SubscriptionId(UUID.fromString(subscriptionIdString));
        var operationId = new OperationId(subscriptionIdString + "-" + elementId);

        var activatedJob = mock(ActivatedJob.class);
        when(activatedJob.getElementId()).thenReturn(elementId);

        doNothing().when(useCase).sendConfirmationMail(subscriptionId, operationId);

        // When
        underTest.sendConfirmationMail(activatedJob, subscriptionIdString);

        // Then
        verify(useCase, times(1)).sendConfirmationMail(subscriptionId, operationId);
        verifyNoMoreInteractions(useCase);
    }
}
