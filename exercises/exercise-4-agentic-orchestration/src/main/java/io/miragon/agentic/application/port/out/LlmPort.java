package io.miragon.agentic.application.port.out;

public interface LlmPort {
    String chat(String systemMessage, String userMessage);
}
