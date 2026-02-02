package de.emaarco.example.adapter.outbound.zeebe.bike;

import de.emaarco.example.adapter.process.TestProcessEngineConfiguration;
import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi;
import de.emaarco.example.application.port.inbound.bike.CheckBikeAvailabilityUseCase;
import de.emaarco.example.application.port.inbound.bike.NotifyBikeCancelationUseCase;
import de.emaarco.example.application.port.inbound.bike.SendBikeConfirmationMailUseCase;
import de.emaarco.example.application.port.inbound.bike.SendBikeWelcomeMailUseCase;
import de.emaarco.example.application.port.inbound.bike.SendPaymentReminderUseCase;
import de.emaarco.example.application.port.inbound.bike.SendRejectionMailUseCase;
import de.emaarco.example.application.port.inbound.bike.ShipBikeUseCase;
import de.emaarco.example.domain.bike.BikeSubscriptionId;
import io.camunda.client.CamundaClient;
import io.camunda.client.api.response.ProcessInstanceEvent;
import io.camunda.process.test.api.CamundaAssert;
import io.camunda.process.test.api.CamundaProcessTestContext;
import io.camunda.process.test.api.CamundaSpringProcessTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

import static de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi.Elements.*;
import static de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi.Messages.MESSAGE_BIKE_RECEIVED;
import static de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi.Messages.MESSAGE_PAYMENT_RECEIVED;
import static de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi.Variables.SUBSCRIPTION_ID;
import static io.camunda.process.test.api.assertions.ProcessInstanceSelectors.byKey;
import static org.mockito.Mockito.*;

/**
 * Process test scaffolding for the Bike Subscription Process.
 * This is a training exercise - implement the 4 test cases following the pattern from NewsletterSubscriptionProcessTest.
 * <p>
 * Uses native Camunda 8.8 test API with @CamundaSpringProcessTest and Spring Boot for component injection.
 * Workers are automatically registered via @JobWorker annotation.
 * Uses H2 an in-memory database for testing (configured in test/resources/application.yaml).
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@CamundaSpringProcessTest
@Import(TestProcessEngineConfiguration.class)
class BikeSubscriptionProcessTest {
	
	@Autowired
	private CamundaClient camundaClient;
	
	@Autowired
	private CamundaProcessTestContext processTestContext;
	
	@Autowired
	private BikeSubscriptionProcessAdapter processPort;
	
	@MockitoBean
	private CheckBikeAvailabilityUseCase checkAvailabilityUseCase;
	
	@MockitoBean
	private SendRejectionMailUseCase sendRejectionMailUseCase;
	
	@MockitoBean
	private SendBikeConfirmationMailUseCase sendConfirmationMailUseCase;
	
	@MockitoBean
	private SendPaymentReminderUseCase sendPaymentReminderUseCase;
	
	@MockitoBean
	private NotifyBikeCancelationUseCase notifyCancellationUseCase;
	
	@MockitoBean
	private ShipBikeUseCase shipBikeUseCase;
	
	@MockitoBean
	private SendBikeWelcomeMailUseCase sendWelcomeMailUseCase;
	
	@BeforeEach
	void setup() {
		// No stubbing needed for void methods in Mockito
		when(checkAvailabilityUseCase.checkAvailability(any())).thenReturn(true);
	}
	
	@AfterEach
	void confirmCalls() {
		verifyNoMoreInteractions(
				checkAvailabilityUseCase,
				sendRejectionMailUseCase,
				sendConfirmationMailUseCase,
				sendPaymentReminderUseCase,
				notifyCancellationUseCase,
				shipBikeUseCase,
				sendWelcomeMailUseCase
		);
	}
	
	@Test
	void happyPath() {
		
		// given: running process
		BikeSubscriptionId subscriptionId = new BikeSubscriptionId(UUID.randomUUID());
		long instanceKey = processPort.startSubscription(subscriptionId);
		var instance = byKey(instanceKey);
		
		// when: payment is received
		CamundaAssert.assertThat(instance).isWaitingForMessage(MESSAGE_PAYMENT_RECEIVED);
		processPort.sendPaymentReceived(subscriptionId);
		
		// when: bike is received
		CamundaAssert.assertThat(instance).isWaitingForMessage(MESSAGE_BIKE_RECEIVED);
		processPort.sendBikeReceived(subscriptionId);
		
		// then: process should complete successfully
		CamundaAssert.assertThat(instance)
				.isCompleted()
				.hasCompletedElements(
						START_EVENT_SUBSCRIPTION_REQUESTED,
						ACTIVITY_CHECK_AVAILABILITY,
						GATEWAY_BIKE_AVAILABLE,
						ACTIVITY_SEND_CONFIRMATION_MAIL,
						ACTIVITY_WAIT_FOR_PAYMENT,
						ACTIVITY_SHIP_BIKE,
						ACTIVITY_WAIT_FOR_DELIVERY,
						ACTIVITY_SEND_WELCOME_MAIL,
						END_EVENT_SUBSCRIPTION_ACTIVE
				);
		
		verify(checkAvailabilityUseCase).checkAvailability(subscriptionId);
		verify(sendConfirmationMailUseCase).sendConfirmationMail(subscriptionId);
		verify(shipBikeUseCase).shipBike(subscriptionId);
		verify(sendWelcomeMailUseCase).sendWelcomeMail(subscriptionId);
	}
	
	@Test
	void informCustomerThatBikeIsNotAvailable() {
		
		// given: bike is not available
		BikeSubscriptionId subscriptionId = new BikeSubscriptionId(UUID.randomUUID());
		when(checkAvailabilityUseCase.checkAvailability(any())).thenReturn(false);
		
		// when: subscription is started
		long instanceKey = processPort.startSubscription(subscriptionId);
		var instance = byKey(instanceKey);
		
		// then: process should complete with rejection path
		CamundaAssert.assertThat(instance)
				.isCompleted()
				.hasCompletedElements(
						START_EVENT_SUBSCRIPTION_REQUESTED,
						ACTIVITY_CHECK_AVAILABILITY,
						ACTIVITY_SEND_REJECTION_MAIL,
						END_EVENT_OFFER_NOT_POSSIBLE
				);
		
		verify(checkAvailabilityUseCase).checkAvailability(subscriptionId);
		verify(sendRejectionMailUseCase).sendRejectionMail(subscriptionId);
		verify(sendConfirmationMailUseCase, never()).sendConfirmationMail(any());
		verify(sendPaymentReminderUseCase, never()).sendPaymentReminder(any());
		verify(notifyCancellationUseCase, never()).notifyCancelation(any());
		verify(shipBikeUseCase, never()).shipBike(any());
		verify(sendWelcomeMailUseCase, never()).sendWelcomeMail(any());
	}
	
	@Test
	void informCustomerAboutPayment() {
		// given: instance that is waiting for payment
		BikeSubscriptionId subscriptionId = new BikeSubscriptionId(UUID.randomUUID());
		var instance = startProcessAt(ACTIVITY_WAIT_FOR_PAYMENT, subscriptionId);
		
		// then: process should wait for payment
		CamundaAssert.assertThat(instance)
				.isWaitingForMessage(MESSAGE_PAYMENT_RECEIVED);
		
		// when: three days are passing
		Duration atLeast3Days = Duration.ofDays(3);
		processTestContext.increaseTime(atLeast3Days);
		
		// then: a reminder should be sent
		CamundaAssert.assertThat(instance)
				.isWaitingForMessage(MESSAGE_PAYMENT_RECEIVED)
				.hasCompletedElements(
						TIMER_EVERY_3_DAYS,
						ACTIVITY_SEND_PAYMENT_REMINDER,
						END_EVENT_CUSTOMER_REMINDED
				);
		
		verify(sendPaymentReminderUseCase).sendPaymentReminder(subscriptionId);
	}
	
	@Test
	void informCustomerAboutCancelation() {
		
		// given: instance that is waiting for payment
		BikeSubscriptionId subscriptionId = new BikeSubscriptionId(UUID.randomUUID());
		var instance = startProcessAt(ACTIVITY_WAIT_FOR_PAYMENT, subscriptionId);
		CamundaAssert.assertThat(instance).isWaitingForMessage(MESSAGE_PAYMENT_RECEIVED);
		
		// when: customer aborts the subscription request
		processPort.sendRequestCanceled(subscriptionId);
		
		// then: abortion processed successfully
		CamundaAssert.assertThat(instance)
				.isCompleted()
				.hasTerminatedElement(ACTIVITY_WAIT_FOR_PAYMENT, 1)
				.hasCompletedElements(
						MESSAGE_REQUEST_CANCELED_EVENT,
						ACTIVITY_NOTIFY_ABOUT_CANCELATION,
						END_EVENT_REQUEST_CANCELED
				);
		
		verify(notifyCancellationUseCase).notifyCancelation(subscriptionId);
	}
	
	private ProcessInstanceEvent startProcessAt(String elementId, BikeSubscriptionId subscriptionId) {
		var variables = Map.of(SUBSCRIPTION_ID, subscriptionId.value().toString());
		return camundaClient.newCreateInstanceCommand()
				.bpmnProcessId(BikeSubscriptionSignupProcessApi.PROCESS_ID)
				.latestVersion()
				.variables(variables)
				.startBeforeElement(elementId)
				.send()
				.join();
	}
}
