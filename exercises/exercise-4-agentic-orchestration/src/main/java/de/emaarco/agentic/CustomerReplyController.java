package de.emaarco.agentic;

import io.camunda.client.CamundaClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

@RestController
public class CustomerReplyController {

    private final CamundaClient camundaClient;

    public CustomerReplyController(CamundaClient camundaClient) {
        this.camundaClient = camundaClient;
    }

    @PostMapping("/customer-reply")
    public ResponseEntity<String> handleCustomerReply(@RequestBody CustomerReplyRequest request) {
        camundaClient.newPublishMessageCommand()
            .messageName("customerUpdate")
            .correlationKey(request.customerNo())
            .variables(Map.of(
                "orderId", request.orderId(),
                "reply", request.reply()
            ))
            .timeToLive(Duration.ofSeconds(10))
            .send()
            .join();

        return ResponseEntity.ok("Message correlated for customer: " + request.customerNo());
    }
}
