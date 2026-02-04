package io.miragon.agentic.application.port.in;

public record GenerateTextCommand(
    String systemMessage,
    String prompt
) {}
