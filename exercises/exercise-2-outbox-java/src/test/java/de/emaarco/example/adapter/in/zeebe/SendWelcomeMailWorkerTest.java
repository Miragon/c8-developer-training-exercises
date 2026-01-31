package de.emaarco.example.adapter.in.zeebe;

import de.emaarco.example.application.port.in.SendWelcomeMailUseCase;
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
 * Unit test for SendWelcomeMailWorker in idempotency-pattern.
 * Tests that the worker correctly extracts variables, generates operation ID, and calls the use case.
 */
@ExtendWith(MockitoExtension.class)
class SendWelcomeMailWorkerTest {

    @Mock
    private SendWelcomeMailUseCase useCase;

    @InjectMocks
    private SendWelcomeMailWorker underTest;

    @Test
    void shouldSendWelcomeMailWithOperationIdWhenJobIsReceived() {
        // Given
        var subscriptionIdString = "123e4567-e89b-12d3-a456-426614174000";
        var elementId = "Activity_SendWelcomeMail";
        var subscriptionId = new SubscriptionId(UUID.fromString(subscriptionIdString));
        var operationId = new OperationId(subscriptionIdString + "-" + elementId);

        var activatedJob = mock(ActivatedJob.class);
        when(activatedJob.getElementId()).thenReturn(elementId);

        doNothing().when(useCase).sendWelcomeMail(subscriptionId, operationId);

        // When
        underTest.sendWelcomeMail(activatedJob, subscriptionIdString);

        // Then
        verify(useCase, times(1)).sendWelcomeMail(subscriptionId, operationId);
        verifyNoMoreInteractions(useCase);
    }
}
