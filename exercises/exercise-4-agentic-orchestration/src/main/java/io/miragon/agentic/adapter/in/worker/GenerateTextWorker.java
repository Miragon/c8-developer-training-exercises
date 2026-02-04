package io.miragon.agentic.adapter.in.worker;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import io.miragon.agentic.application.port.in.GenerateTextCommand;
import io.miragon.agentic.application.port.in.GenerateTextUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GenerateTextWorker {

    private static final Logger log = LoggerFactory.getLogger(GenerateTextWorker.class);

    private final GenerateTextUseCase useCase;

    public GenerateTextWorker(GenerateTextUseCase useCase) {
        this.useCase = useCase;
    }

    @JobWorker(type = "generateText")
    public Map<String, Object> handle(
            @Variable("systemMessage") String systemMessage,
            @Variable("prompt") String prompt
    ) {
        log.info("Received job to generate text with prompt: {}",
            prompt.length() > 50 ? prompt.substring(0, 50) + "..." : prompt);

        String response = useCase.generateText(new GenerateTextCommand(systemMessage, prompt));

        return Map.of("response", response);
    }
}
