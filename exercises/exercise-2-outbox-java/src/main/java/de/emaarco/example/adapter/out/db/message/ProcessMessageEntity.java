package de.emaarco.example.adapter.out.db.message;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

/**
 * Outbox table for reliable message delivery to Zeebe.
 * Messages are stored in the database in the same transaction as business data,
 * then a background scheduler sends them to Zeebe.
 */
@Entity(name = "process_message")
public class ProcessMessageEntity {

    @Id
    private UUID messageId;

    // TODO: Add messageName: String
    // HINT: This is the Zeebe message name (e.g., "Message_FormSubmitted")

    // TODO: Add correlationId: String
    // HINT: This is the subscription ID used for message correlation in Zeebe

    // TODO: Add variables: String
    // HINT: JSON serialized process variables (use empty string as default)

    // TODO: Add status: MessageStatus enum with @Enumerated(EnumType.STRING)
    // HINT: Default value should be MessageStatus.PENDING

    // TODO: Add retryCount: int
    // HINT: Track how many times we tried to send this message (default 0)

    // TODO: Add createdAt: LocalDateTime
    // HINT: Timestamp when message was created (default LocalDateTime.now())

    // TODO: Add updatedAt: LocalDateTime
    // HINT: Timestamp when message was last updated (default LocalDateTime.now())

    public ProcessMessageEntity() {
    }

    public ProcessMessageEntity(UUID messageId) {
        this.messageId = messageId;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }
}
