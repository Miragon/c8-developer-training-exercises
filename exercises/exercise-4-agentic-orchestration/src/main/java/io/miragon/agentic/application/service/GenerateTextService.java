package io.miragon.agentic.application.service;

import io.miragon.agentic.application.port.in.GenerateTextCommand;
import io.miragon.agentic.application.port.in.GenerateTextUseCase;
import io.miragon.agentic.application.port.out.LlmPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GenerateTextService implements GenerateTextUseCase {

    private static final Logger log = LoggerFactory.getLogger(GenerateTextService.class);

    private final LlmPort llmPort;

    public GenerateTextService(LlmPort llmPort) {
        this.llmPort = llmPort;
    }

    @Override
    public String generateText(GenerateTextCommand command) {
        log.info("Generating text with system message: {}", truncate(command.systemMessage(), 50));

        String response = llmPort.chat(command.systemMessage(), command.prompt());

        log.info("Generated response: {}", truncate(response, 100));
        return response;
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }
}
