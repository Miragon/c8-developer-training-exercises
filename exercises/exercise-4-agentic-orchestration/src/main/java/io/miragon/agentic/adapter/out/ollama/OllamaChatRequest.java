package io.miragon.agentic.adapter.out.ollama;

import java.util.List;

public record OllamaChatRequest(
    String model,
    List<Message> messages,
    boolean stream,
    Options options
) {
    public record Message(String role, String content) {}
    public record Options(double temperature) {}

    public static OllamaChatRequest of(String model, String systemMessage, String userMessage, double temperature) {
        return new OllamaChatRequest(
            model,
            List.of(
                new Message("system", systemMessage),
                new Message("user", userMessage)
            ),
            false,
            new Options(temperature)
        );
    }
}
