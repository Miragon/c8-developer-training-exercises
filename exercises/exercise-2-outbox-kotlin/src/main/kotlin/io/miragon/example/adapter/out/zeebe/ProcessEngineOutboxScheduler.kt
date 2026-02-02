package io.miragon.example.adapter.out.zeebe

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.miragon.example.adapter.out.db.message.MessageStatus
import io.miragon.example.adapter.out.db.message.ProcessMessageEntity
import io.miragon.example.adapter.out.db.message.ProcessMessageJpaRepository
import io.camunda.client.CamundaClient
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import java.time.Duration

/**
 * Background scheduler that processes the outbox table and sends messages to Zeebe.
 * Runs every 200ms to ensure low latency while preventing overwhelming the database.
 */
@Component
class ProcessEngineOutboxScheduler(
    private val camundaClient: CamundaClient,
    private val repository: ProcessMessageJpaRepository,
    private val transactionTemplate: TransactionTemplate,
    private val objectMapper: ObjectMapper
) {
    private val log = KotlinLogging.logger {}

    // TODO: Add @Scheduled annotation with fixedDelay = 200 (milliseconds)
    // HINT: This makes the method run every 200ms after the previous execution completes
    fun sendMessages() {
        TODO("Implement scheduler loop")
        // HINT: Use while loop to process messages one-at-a-time until queue is empty
        // HINT: Call processNextMessage() in loop
        // HINT: Stop when processNextMessage() returns false (no more pending messages)
        //
        // Example structure:
        // while (processNextMessage()) {
        //     // Keep processing until no messages left
        // }
    }

    private fun processNextMessage(): Boolean {
        TODO("Implement single message processing with transaction boundary")
        // HINT: Use transactionTemplate.execute { } to wrap the database operations
        // HINT: Inside transaction:
        //   1. Find next PENDING message with lock using repository.findFirstByStatusWithLock()
        //   2. If no message found, return false
        //   3. Try to send message to Zeebe using sendMessage()
        //   4. On success: update status to SENT and save
        //   5. On failure: increment retryCount and save (keep status as PENDING)
        // HINT: Return true if message processed, false if none found
        //
        // Why transaction boundary here?
        // - Ensures status update is committed before next scheduler run
        // - Prevents duplicate processing if scheduler crashes mid-flight
        //
        // Why try-catch?
        // - Zeebe may be unavailable (network issues, gateway down)
        // - We want to retry later, not lose the message
    }

    private fun sendMessage(message: ProcessMessageEntity) {
        TODO("Implement Zeebe message publishing")
        // HINT: Construct messageId = "${message.correlationId}-${message.messageName}"
        // HINT: Deserialize message.variables from JSON to Map<String, Any>
        // HINT: Use objectMapper.readValue(message.variables, object : TypeReference<Map<String, Any>>() {})
        // HINT: Use camundaClient.newPublishMessageCommand()
        // HINT: Set messageName, correlationKey (use correlationId), messageId, variables
        // HINT: Set TTL to 10 seconds using .timeToLive(Duration.ofSeconds(10))
        // HINT: Call .send().join() to send synchronously
        // HINT: Log success using log.info { "Sent message..." }
        //
        // Why messageId?
        // - Zeebe uses messageId for deduplication (same messageId within TTL is ignored)
        // - Prevents duplicate process starts if scheduler retries
        //
        // Why TTL (Time To Live)?
        // - Zeebe stores messageId for deduplication only during TTL period
        // - After TTL expires, same messageId can be used again
    }
}
