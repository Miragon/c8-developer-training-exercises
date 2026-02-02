package io.miragon.agentic.adapter.out.ollama;

import io.miragon.agentic.application.port.out.LlmPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class OllamaAdapter implements LlmPort {

    private static final Logger log = LoggerFactory.getLogger(OllamaAdapter.class);

    private final RestClient restClient;
    private final OllamaConfig config;

    public OllamaAdapter(OllamaConfig config) {
        this.config = config;
        this.restClient = RestClient.builder()
            .baseUrl(config.getBaseUrl())
            .build();
    }

    @Override
    public String chat(String systemMessage, String userMessage) {
        log.debug("Calling Ollama API with model: {}", config.getModel());

        OllamaChatRequest request = OllamaChatRequest.of(
            config.getModel(),
            systemMessage,
            userMessage,
            config.getTemperature()
        );

        try {
            OllamaChatResponse response = restClient.post()
                .uri("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(OllamaChatResponse.class);

            if (response == null) {
                throw new RuntimeException("Received null response from Ollama");
            }

            return response.getContent();

        } catch (Exception e) {
            log.error("Failed to call Ollama API", e);
            throw new RuntimeException("Failed to generate text: " + e.getMessage(), e);
        }
    }
}
