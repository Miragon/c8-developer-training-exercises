package de.emaarco.example.adapter.process

import com.ninjasquad.springmockk.MockkBean
import de.emaarco.example.adapter.outbound.zeebe.bike.BikeSubscriptionProcessAdapter
import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi
import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi.Elements
import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi.Messages
import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi.Variables
import de.emaarco.example.application.port.inbound.bike.CheckBikeAvailabilityUseCase
import de.emaarco.example.application.port.inbound.bike.NotifyBikeCancelationUseCase
import de.emaarco.example.application.port.inbound.bike.SendBikeConfirmationMailUseCase
import de.emaarco.example.application.port.inbound.bike.SendBikeWelcomeMailUseCase
import de.emaarco.example.application.port.inbound.bike.SendPaymentReminderUseCase
import de.emaarco.example.application.port.inbound.bike.SendRejectionMailUseCase
import de.emaarco.example.application.port.inbound.bike.ShipBikeUseCase
import de.emaarco.example.domain.bike.BikeSubscriptionId
import io.camunda.client.CamundaClient
import io.camunda.process.test.api.CamundaAssert
import io.camunda.process.test.api.CamundaProcessTestContext
import io.camunda.process.test.api.CamundaSpringProcessTest
import io.camunda.process.test.api.assertions.ProcessInstanceSelectors
import io.mockk.Runs
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.time.Duration

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
    private lateinit var camundaClient: CamundaClient

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
    private lateinit var notifyCancellationUseCase: NotifyBikeCancelationUseCase

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
        every { notifyCancellationUseCase.notifyCancelation(any()) } just Runs
        every { shipBikeUseCase.shipBike(any()) } just Runs
        every { sendWelcomeMailUseCase.sendWelcomeMail(any()) } just Runs
    }

    @AfterEach
    fun confirmCalls() = confirmVerified(
        checkAvailabilityUseCase, sendRejectionMailUseCase,
        sendConfirmationMailUseCase, sendPaymentReminderUseCase,
        notifyCancellationUseCase, shipBikeUseCase, sendWelcomeMailUseCase
    )

    @Test
    fun `happy path`() {

        // given: active instance
        val subscriptionId = BikeSubscriptionId()
        val instanceKey = processPort.startSubscription(id = subscriptionId)
        val instance = ProcessInstanceSelectors.byKey(instanceKey)

        // when: payment is received
        CamundaAssert.assertThat(instance).isWaitingForMessage(Messages.MESSAGE_PAYMENT_RECEIVED)
        processPort.sendPaymentReceived(subscriptionId)

        // when: bike is received
        CamundaAssert.assertThat(instance).isWaitingForMessage(Messages.MESSAGE_BIKE_RECEIVED)
        processPort.sendBikeReceived(subscriptionId)

        // then: process completes successfully
        CamundaAssert.assertThat(instance)
            .isCompleted()
            .hasCompletedElements(
                Elements.START_EVENT_SUBSCRIPTION_REQUESTED,
                Elements.ACTIVITY_CHECK_AVAILABILITY,
                Elements.GATEWAY_BIKE_AVAILABLE,
                Elements.ACTIVITY_WAIT_FOR_PAYMENT,
                Elements.ACTIVITY_SHIP_BIKE,
                Elements.ACTIVITY_WAIT_FOR_DELIVERY,
                Elements.ACTIVITY_SEND_WELCOME_MAIL,
                Elements.END_EVENT_SUBSCRIPTION_ACTIVE,
            )

        verify { checkAvailabilityUseCase.checkAvailability(subscriptionId) }
        verify { sendConfirmationMailUseCase.sendConfirmationMail(subscriptionId) }
        verify { shipBikeUseCase.shipBike(subscriptionId) }
        verify { sendWelcomeMailUseCase.sendWelcomeMail(subscriptionId) }
    }

    @Test
    fun `inform customer that bike is not available`() {

        // given: the bike is not available
        val subscriptionId = BikeSubscriptionId()
        every { checkAvailabilityUseCase.checkAvailability(any()) } returns false

        // when: subscription is started
        val id = processPort.startSubscription(id = subscriptionId)
        val instance = ProcessInstanceSelectors.byKey(id)

        // then: process completes with rejection path
        CamundaAssert.assertThat(instance)
            .isCompleted()
            .hasCompletedElements(
                Elements.START_EVENT_SUBSCRIPTION_REQUESTED,
                Elements.ACTIVITY_CHECK_AVAILABILITY,
                Elements.ACTIVITY_SEND_REJECTION_MAIL,
                Elements.END_EVENT_OFFER_NOT_POSSIBLE
            )

        // then
        verify { checkAvailabilityUseCase.checkAvailability(subscriptionId) }
        verify { sendRejectionMailUseCase.sendRejectionMail(subscriptionId) }
    }

    @Test
    fun `inform customer about payment`() {

        // given: instance that is waiting for payment
        val subscriptionId = BikeSubscriptionId()
        val instance = startProcessAt(Elements.ACTIVITY_WAIT_FOR_PAYMENT, subscriptionId)
        CamundaAssert.assertThat(instance).isWaitingForMessage(Messages.MESSAGE_PAYMENT_RECEIVED)

        // when: three days are passing
        val atLeast3Days = Duration.ofDays(3)
        processTestContext.increaseTime(atLeast3Days)

        // then: reminder should be sent
        CamundaAssert.assertThat(instance)
            .isWaitingForMessage(Messages.MESSAGE_PAYMENT_RECEIVED)
            .hasCompletedElements(
                Elements.TIMER_EVERY_3_DAYS,
                Elements.ACTIVITY_SEND_PAYMENT_REMINDER,
                Elements.END_EVENT_CUSTOMER_REMINDED
            )

        verify { sendPaymentReminderUseCase.sendPaymentReminder(subscriptionId) }
    }

    @Test
    fun `inform customer about cancellation`() {

        // given: instance that is waiting for payment
        val subscriptionId = BikeSubscriptionId()
        val instance = startProcessAt(Elements.ACTIVITY_WAIT_FOR_PAYMENT, subscriptionId)
        CamundaAssert.assertThat(instance).isWaitingForMessage(Messages.MESSAGE_PAYMENT_RECEIVED)

        // when: customer aborts the subscription request
        processPort.sendRequestCanceled(subscriptionId)

        // then: abortion processed successfully
        CamundaAssert.assertThat(instance)
            .isCompleted()
            .hasTerminatedElement(Elements.ACTIVITY_WAIT_FOR_PAYMENT, 1)
            .hasCompletedElements(
                Elements.MESSAGE_REQUEST_CANCELED_EVENT,
                Elements.ACTIVITY_NOTIFY_ABOUT_CANCELATION,
                Elements.END_EVENT_REQUEST_CANCELED
            )

        verify { notifyCancellationUseCase.notifyCancelation(subscriptionId) }
    }

    private fun startProcessAt(
        elementId: String,
        subscriptionId: BikeSubscriptionId
    ) = camundaClient.newCreateInstanceCommand()
        .bpmnProcessId(BikeSubscriptionSignupProcessApi.PROCESS_ID)
        .latestVersion()
        .variables(mapOf(Variables.SUBSCRIPTION_ID to subscriptionId.value.toString()))
        .startBeforeElement(elementId)
        .send()
        .join()
}