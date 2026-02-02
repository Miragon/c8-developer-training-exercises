package io.miragon.example.adapter.in.zeebe;

import io.miragon.example.application.port.in.AbortSubscriptionUseCase;
import io.miragon.example.domain.OperationId;
import io.miragon.example.domain.SubscriptionId;
import io.camunda.client.api.response.ActivatedJob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

/**
 * Unit test for AbortSubscriptionWorker in idempotency-pattern.
 * Tests that the worker correctly extracts variables, generates operation ID, and calls the use case.
 */
@ExtendWith(MockitoExtension.class)
class AbortSubscriptionWorkerTest {

    @Mock
    private AbortSubscriptionUseCase useCase;

    @InjectMocks
    private AbortSubscriptionWorker underTest;

    @Test
    void shouldAbortSubscriptionWithOperationIdWhenJobIsReceived() {
        // Given
        var subscriptionIdString = "123e4567-e89b-12d3-a456-426614174000";
        var elementId = "Activity_AbortRegistration";
        var subscriptionId = new SubscriptionId(UUID.fromString(subscriptionIdString));
        var operationId = new OperationId(subscriptionIdString + "-" + elementId);

        var activatedJob = mock(ActivatedJob.class);
        when(activatedJob.getElementId()).thenReturn(elementId);

        doNothing().when(useCase).abort(subscriptionId, operationId);

        // When
        underTest.abortRegistration(activatedJob, subscriptionIdString);

        // Then
        verify(useCase, times(1)).abort(subscriptionId, operationId);
        verifyNoMoreInteractions(useCase);
    }
}
