package de.emaarco.example.adapter.outbound.zeebe.bike;

import de.emaarco.example.adapter.process.TestProcessEngineConfiguration;
import de.emaarco.example.application.port.inbound.bike.*;
import io.camunda.process.test.api.CamundaProcessTestContext;
import io.camunda.process.test.api.CamundaSpringProcessTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Process test scaffolding for the Bike Subscription Process.
 * This is a training exercise - implement the 4 test cases following the pattern from NewsletterSubscriptionProcessTest.
 *
 * Uses native Camunda 8.8 test API with @CamundaSpringProcessTest and Spring Boot for component injection.
 * Workers are automatically registered via @JobWorker annotation.
 * Uses H2 an in-memory database for testing (configured in test/resources/application.yaml).
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@CamundaSpringProcessTest
@Import(TestProcessEngineConfiguration.class)
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
