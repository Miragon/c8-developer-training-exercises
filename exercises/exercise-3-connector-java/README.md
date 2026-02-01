# Workshop: Camunda Connectors unleashed - Crafting future-proof low-code solutions

## Abstract
Join us for practical insight into the world of Camunda connectors, and learn how to best use them cleanly and efficiently
in low-code environments. We’ll look at the common challenges of using connectors and explain how to develop your own that
are sustainable and suited to specific needs of our business. We will also dive into development to understand the Camunda
connector ecosystem holistically, and put all the pieces of the puzzle together to design a holistic clean process architecture
that can be operated sustainably and is future-proofed with Camunda connectors.

## Introduction
### Why bother with Connectors?
Want to democratize software development? Camunda Connectors are the key.
Camunda Connectors allow users to create reusable pieces of code. These pieces can be used in various processes.

This means business users can create and automate processes without needing technical skills.
Here are the key benefits:
- **Reusable Code**: Save time and effort by using the same code in different processes.
- **Democratize Development**: Make process automation accessible to everyone in your organization.
- **Overcoming Developer Shortage**: The limited resources are always the developers. Low-code and connectors enable organisation to overcome this shortage
- **Better Communication**: Model-based development makes it possible to have an abstraction to the code and thus ensures a common language that both business and IT can understand.

Though, crafting these reusable connectors in a sustainable fashion can be a challenge.

### What is a Camunda Connector?
A Connector is a reusable, environment agnostic piece of code. It can either be inbound or outbound.
- **Inbound Connector**: Enable workflows to receive data or messages from external systems or services.
- **Outbound Connector**: Enables workflows to trigger the external systems or services.

Camunda offers out-of-the-box connectors, though you can also implement your own.

### Why implementing Connectors in a sustainable fashion?
Building sustainable connectors for better automation.
Creating sustainable connectors is a game-changer in our automation journey. Why is this so crucial? It allows us to decouple our external systems from our process (BPMN), ensuring our business processes stay stable and unbiased.

When we rely on out-of-the-box connectors, we can encounter tight coupling issues. For instance, a Stripe connector would embed Stripe-specific details, which we should avoid to keep our processes clean and resilient.

Here’s how we can achieve sustainable connectors:
- Domain Activities: Prioritize domain activities over technical ones. This approach keeps our BPMN clean, stable, and focused on core business functions.
- Avoid Dependencies: Reduce dependencies on external systems. By doing so, we lower risks and enhance our system's flexibility.
- Stable Processes: Ensure that our business processes remain stable even when external systems undergo changes. This is key to maintaining a robust automation framework.

Fast-forward to implementing these strategies, we create a robust and adaptable automation system. This approach allows us to face fewer disruptions and maintain a seamless workflow.

## Workshop

### Scenario
Miravelo wants a custom connector using the Camunda Connector SDK. The connector should notify a customer based on their preferences stored in the CRM system.

### Goal
From BPMN we only configure the **customer number** and **message**. Everything else is handled inside the connector. This keeps our process model clean and domain-focused while the connector encapsulates all technical complexity.

### What BPMN Provides
| Input | Description |
|-------|-------------|
| Customer Number | Unique identifier to look up the customer |
| Message | The notification content to send |

### What the Connector Does
| Step | Description |
|------|-------------|
| Fetch preferences | Retrieve customer preferences from CRM |
| Select channel | Determine the preferred channel (email, SMS, or push) |
| Send notification | Deliver the message via the selected channel |
| Return result | Provide a friendly result back to the process |

### Exercise Steps

1. **Create a connector project** from the Java template
2. **Define input and output classes** for the connector interface
3. **Implement the function** with mocked clients for CRM and notification services
4. **Add local tests** for mapping and channel selection logic
5. **Generate or write the element template** to configure the connector in BPMN
6. **Run it in a connector runtime** and call it from your BPMN process

### Resources

| Resource | Link |
|----------|------|
| Connector templates and examples | https://github.com/camunda/connectors |
| Java Connector SDK template | https://github.com/camunda/connector-template-outbound |
| Element template specification | https://docs.camunda.io/docs/components/connectors/custom-built-connectors/connector-templates/ |

### Architecture Guidance
To build a sustainable connector we recommend using a hexagonal architecture. This keeps domain-specific data transformations separate from the API requirements of third-party applications.

Using "Input Adapters" (for receiving connector requests) and "Output Adapters" (for CRM and notification services) enables reusability and allows swapping vendor-specific implementations without changing the core logic.




