package io.miragon.example.application.service;

import io.miragon.example.application.port.out.NewsletterSubscriptionRepository;
import io.miragon.example.application.port.out.ProcessedOperationRepository;
import io.miragon.example.domain.Email;
import io.miragon.example.domain.Name;
import io.miragon.example.domain.NewsletterId;
import io.miragon.example.domain.NewsletterSubscription;
import io.miragon.example.domain.OperationId;
import io.miragon.example.domain.SubscriptionId;
import io.miragon.example.domain.SubscriptionStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
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
@DisabledIfSystemProperty(named = "test.profile", matches = "ci")
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
