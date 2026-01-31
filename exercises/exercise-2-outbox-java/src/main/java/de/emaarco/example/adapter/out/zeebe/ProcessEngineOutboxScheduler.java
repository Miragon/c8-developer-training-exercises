package de.emaarco.example.adapter.out.zeebe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.emaarco.example.adapter.out.db.message.MessageStatus;
import de.emaarco.example.adapter.out.db.message.ProcessMessageEntity;
import de.emaarco.example.adapter.out.db.message.ProcessMessageJpaRepository;
import io.camunda.client.CamundaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.Duration;
import java.util.Map;

/**
 * Background scheduler that processes the outbox table and sends messages to Zeebe.
 * Runs every 200ms to ensure low latency while preventing overwhelming the database.
 */
@Component
public class ProcessEngineOutboxScheduler {

    private static final Logger log = LoggerFactory.getLogger(ProcessEngineOutboxScheduler.class);

    private final CamundaClient camundaClient;
    private final ProcessMessageJpaRepository repository;
    private final TransactionTemplate transactionTemplate;
    private final ObjectMapper objectMapper;

    public ProcessEngineOutboxScheduler(
            CamundaClient camundaClient,
            ProcessMessageJpaRepository repository,
            TransactionTemplate transactionTemplate,
            ObjectMapper objectMapper
    ) {
        this.camundaClient = camundaClient;
        this.repository = repository;
        this.transactionTemplate = transactionTemplate;
        this.objectMapper = objectMapper;
    }

    // TODO: Add @Scheduled annotation with fixedDelay = 200 (milliseconds)
    // HINT: This makes the method run every 200ms after the previous execution completes
    public void sendMessages() {
        throw new UnsupportedOperationException("TODO: Implement scheduler loop");
        // HINT: Use while loop to process messages one-at-a-time until queue is empty
        // HINT: Call processNextMessage() in loop
        // HINT: Stop when processNextMessage() returns false (no more pending messages)
        //
        // Example structure:
        // while (processNextMessage()) {
        //     // Keep processing until no messages left
        // }
    }

    private boolean processNextMessage() {
        throw new UnsupportedOperationException("TODO: Implement single message processing with transaction boundary");
        // HINT: Use transactionTemplate.execute(status -> { }) to wrap the database operations
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

    private void sendMessage(ProcessMessageEntity message) {
        throw new UnsupportedOperationException("TODO: Implement Zeebe message publishing");
        // HINT: Construct messageId = message.correlationId + "-" + message.messageName
        // HINT: Deserialize message.variables from JSON to Map<String, Object>
        // HINT: Use objectMapper.readValue(message.variables, new TypeReference<Map<String, Object>>() {})
        // HINT: Use camundaClient.newPublishMessageCommand()
        // HINT: Set messageName, correlationKey (use correlationId), messageId, variables
        // HINT: Set TTL to 10 seconds using .timeToLive(Duration.ofSeconds(10))
        // HINT: Call .send().join() to send synchronously
        // HINT: Log success using log.info("Sent message...", ...)
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
