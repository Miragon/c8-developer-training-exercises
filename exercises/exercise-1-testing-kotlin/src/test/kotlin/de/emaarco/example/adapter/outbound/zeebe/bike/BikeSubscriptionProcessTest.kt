package de.emaarco.example.adapter.outbound.zeebe.bike

import com.ninjasquad.springmockk.MockkBean
import de.emaarco.example.application.port.inbound.bike.CheckBikeAvailabilityUseCase
import de.emaarco.example.application.port.inbound.bike.NotifyBikeCancelationUseCase
import de.emaarco.example.application.port.inbound.bike.SendBikeConfirmationMailUseCase
import de.emaarco.example.application.port.inbound.bike.SendBikeWelcomeMailUseCase
import de.emaarco.example.application.port.inbound.bike.SendPaymentReminderUseCase
import de.emaarco.example.application.port.inbound.bike.SendRejectionMailUseCase
import de.emaarco.example.application.port.inbound.bike.ShipBikeUseCase
import de.emaarco.example.adapter.process.TestProcessEngineConfiguration
import io.camunda.process.test.api.CamundaProcessTestContext
import io.camunda.process.test.api.CamundaSpringProcessTest
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.junit.jupiter.api.fail

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
        every { checkAvailabilityUseCase.checkAvailability(any()) } returns true
        every { sendRejectionMailUseCase.sendRejectionMail(any()) } just Runs
        every { sendConfirmationMailUseCase.sendConfirmationMail(any()) } just Runs
        every { sendPaymentReminderUseCase.sendPaymentReminder(any()) } just Runs
        every { notifyCancelationUseCase.notifyCancelation(any()) } just Runs
        every { shipBikeUseCase.shipBike(any()) } just Runs
        every { sendWelcomeMailUseCase.sendWelcomeMail(any()) } just Runs
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
