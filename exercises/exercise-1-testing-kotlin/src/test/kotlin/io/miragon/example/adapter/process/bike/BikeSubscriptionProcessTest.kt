package io.miragon.example.adapter.process.bike

import com.ninjasquad.springmockk.MockkBean
import io.camunda.process.test.api.CamundaProcessTestContext
import io.camunda.process.test.api.CamundaSpringProcessTest
import io.miragon.example.adapter.outbound.zeebe.bike.BikeSubscriptionProcessAdapter
import io.miragon.example.adapter.process.TestProcessEngineConfiguration
import io.miragon.example.application.port.inbound.bike.CheckBikeAvailabilityUseCase
import io.miragon.example.application.port.inbound.bike.NotifyBikeCancelationUseCase
import io.miragon.example.application.port.inbound.bike.SendBikeConfirmationMailUseCase
import io.miragon.example.application.port.inbound.bike.SendBikeWelcomeMailUseCase
import io.miragon.example.application.port.inbound.bike.SendPaymentReminderUseCase
import io.miragon.example.application.port.inbound.bike.SendRejectionMailUseCase
import io.miragon.example.application.port.inbound.bike.ShipBikeUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledIfSystemProperty
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

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
@Import(TestProcessEngineConfiguration::class)
@DisabledIfSystemProperty(named = "test.profile", matches = "ci")
class BikeSubscriptionProcessTest {

    @Autowired
    private lateinit var processTestContext: CamundaProcessTestContext

    @Autowired
    private lateinit var processPort: BikeSubscriptionProcessAdapter

    @MockkBean
    private lateinit var checkAvailabilityUseCase: CheckBikeAvailabilityUseCase

    @MockkBean
    private lateinit var sendRejectionMailUseCase: SendRejectionMailUseCase

    @MockkBean
    private lateinit var sendConfirmationMailUseCase: SendBikeConfirmationMailUseCase

    @MockkBean
    private lateinit var sendPaymentReminderUseCase: SendPaymentReminderUseCase

    @MockkBean
    private lateinit var notifyCancelationUseCase: NotifyBikeCancelationUseCase

    @MockkBean
    private lateinit var shipBikeUseCase: ShipBikeUseCase

    @MockkBean
    private lateinit var sendWelcomeMailUseCase: SendBikeWelcomeMailUseCase

    @BeforeEach
    fun setup() {
        // define mocks
    }

    @Test
    fun `happy path`() {
        fail("Implement this test - follow the pattern from NewsletterSubscriptionProcessTest")
    }

    @Test
    fun `inform customer that bike is not available`() {
        fail("Implement this test")
    }

    @Test
    fun `inform customer about payment`() {
        fail("Implement this test")
    }

    @Test
    fun `inform customer about cancelation`() {
        fail("Implement this test")
    }
}