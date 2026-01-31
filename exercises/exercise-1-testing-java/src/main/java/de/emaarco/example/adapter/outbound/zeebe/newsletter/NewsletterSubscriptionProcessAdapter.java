package de.emaarco.example.adapter.outbound.zeebe.newsletter;

import de.emaarco.example.adapter.process.config.ProcessEngineApi;
import de.emaarco.example.adapter.process.generated.NewsletterSubscriptionProcessApi;
import de.emaarco.example.application.port.outbound.newsletter.NewsletterSubscriptionProcess;
import de.emaarco.example.domain.SubscriptionId;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NewsletterSubscriptionProcessAdapter implements NewsletterSubscriptionProcess {

    private final ProcessEngineApi engineApi;

    public NewsletterSubscriptionProcessAdapter(ProcessEngineApi engineApi) {
        this.engineApi = engineApi;
    }

    @Override
    public long submitForm(SubscriptionId id) {
        Map<String, Object> variables = Map.of("subscriptionId", id.value().toString());
        return engineApi.startProcess(
            NewsletterSubscriptionProcessApi.PROCESS_ID,
            variables
        );
    }

    @Override
    public void confirmSubscription(SubscriptionId id) {
        engineApi.sendMessage(
            NewsletterSubscriptionProcessApi.Messages.MESSAGE_SUBSCRIPTION_CONFIRMED,
            id.value().toString()
        );
    }

}
