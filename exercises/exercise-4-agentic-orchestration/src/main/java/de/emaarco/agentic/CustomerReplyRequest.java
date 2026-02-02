package de.emaarco.agentic;

public record CustomerReplyRequest(
    String customerNo,
    String orderId,
    String reply
) {}
