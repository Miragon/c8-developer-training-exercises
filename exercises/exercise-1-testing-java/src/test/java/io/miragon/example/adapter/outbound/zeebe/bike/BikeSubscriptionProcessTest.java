package io.miragon.example.adapter.outbound.zeebe.bike;

import io.camunda.process.test.api.CamundaProcessTestContext;
import io.camunda.process.test.api.CamundaSpringProcessTest;
import io.miragon.example.adapter.process.TestProcessEngineConfiguration;
import io.miragon.example.application.port.inbound.bike.CheckBikeAvailabilityUseCase;
import io.miragon.example.application.port.inbound.bike.NotifyBikeCancelationUseCase;
import io.miragon.example.application.port.inbound.bike.SendBikeConfirmationMailUseCase;
import io.miragon.example.application.port.inbound.bike.SendBikeWelcomeMailUseCase;
import io.miragon.example.application.port.inbound.bike.SendPaymentReminderUseCase;
import io.miragon.example.application.port.inbound.bike.SendRejectionMailUseCase;
import io.miragon.example.application.port.inbound.bike.ShipBikeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.fail;

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
@DisabledIfSystemProperty(named = "test.profile", matches = "ci")
class BikeSubscriptionProcessTest {
	
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
	private NotifyBikeCancelationUseCase notifyCancelationUseCase;
	
	@MockitoBean
	private ShipBikeUseCase shipBikeUseCase;
	
	@MockitoBean
	private SendBikeWelcomeMailUseCase sendWelcomeMailUseCase;
	
	@BeforeEach
	void setup() {
		// TODO: provide mocks
	}
	
	@Test
	void happyPath() {
		fail("Implement this test - follow the pattern from NewsletterSubscriptionProcessTest");
	}
	
	@Test
	void informCustomerThatBikeIsNotAvailable() {
		fail("Implement this test");
	}
	
	@Test
	void informCustomerAboutPayment() {
		fail("Implement this test");
	}
	
	@Test
	void informCustomerAboutCancelation() {
		fail("Implement this test");
	}
}
