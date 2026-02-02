package io.miragon.agentic.adapter.in.rest;

public record CustomerReplyRequest(
    String customerNo,
    String orderId,
    String reply
) {}
