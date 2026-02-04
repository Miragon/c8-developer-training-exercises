package io.miragon.agentic.adapter.out.ollama;

public record OllamaChatResponse(
    Message message
) {
    public record Message(String role, String content) {}

    public String getContent() {
        return message != null ? message.content() : "";
    }
}
