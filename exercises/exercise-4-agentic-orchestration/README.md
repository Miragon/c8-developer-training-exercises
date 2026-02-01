# Exercise 4: Miravelo Agentic Toolbox

## Scenario

A customer contacts **Miravelo** (a premium bike company) about a delivery problem. An AI-powered agent decides which actions to trigger based on the customer message and order context.

### Input Variables
| Variable | Description |
|----------|-------------|
| `customerNo` | Customer identifier |
| `orderId` | Order reference number |
| `customerMessage` | The customer's support message |

### Goal
- Decide on the next best steps using AI
- Trigger the right tools dynamically
- Produce a reply draft for the customer

---

## Architecture Overview

```
                         +---------------------------+
                         |    Support Request        |
                         |    Received               |
                         +------------+--------------+
                                      |
                                      v
                         +------------+--------------+
                         |  AI: Decide on Tools      |
                         |  (OpenAI Connector)       |
                         +------------+--------------+
                                      |
                                      v
                         +------------+--------------+
                         |   Prepare Tool List       |
                         |   (Script Task)           |
                         +------------+--------------+
                                      |
                                      v
                    +----------------------------------+
                    |      Ad-Hoc Subprocess           |
                    |  +----------------------------+  |
                    |  | Tool 1: Get Order Status   |  |
                    |  | Tool 2: Check Inventory    |  |
                    |  | Tool 3: Warranty Check     |  |
                    |  | Tool 4: Draft Reply (AI)   |  |
                    |  | Tool 5: Human Approval     |  |
                    |  | Tool 6: Create Ticket      |  |
                    |  +----------------------------+  |
                    +----------------------------------+
                                      |
                                      v
                         +------------+--------------+
                         |  AI: Anything else?       |
                         +------------+--------------+
                              |              |
                              v              v
                         +-------+      +-------+
                         | Done  |      | Loop  |
                         +-------+      +-------+
```

---

## Tools Inside the Ad-Hoc Subprocess

| Tool | Name | Implementation | Output Variable |
|------|------|----------------|-----------------|
| Tool_1 | Get Order Status | Script Task (FEEL) | `orderStatus` |
| Tool_2 | Check Inventory for Replacement | Script Task (FEEL) | `replacementAvailable` |
| Tool_3 | Decide Warranty Eligibility | Script Task (FEEL) | `isEligableforWarranty` |
| Tool_4 | Draft Customer Reply | OpenAI Connector | `email` |
| Tool_5 | Ask Human for Approval | User Task + Form | `humanOpinion` |
| Tool_6 | Create Ticket for Warehouse | Script Task (FEEL) | `ticketCreated` |

---

## Exercise Steps

### Step 1: Create the BPMN Process

1. Open **Camunda Modeler**
2. Create a new BPMN diagram
3. Add a **Pool** named "Miravelo Customer Support Process"
4. Set the process ID to `Process_CustomerSupport`

### Step 2: Configure the Start Event

Map the incoming variables to process scope:
- `customerNo`
- `orderId`
- `customerMessage`

### Step 3: Add the AI Decision Task

1. Add a **Service Task** named "Decide on Tool to use"
2. Apply the **OpenAI Connector** template
3. Configure:
   - **Authentication**: Bearer token (use `secrets.OPENAI_API_KEY`)
   - **Model**: `gpt-4o`
   - **System Message**:
     ```
     We received a support request from one of our customers.
     We are Miravelo a bike Company, selling high quality products.
     We want to be the best in service quality as well.
     ```
   - **Prompt** (FEEL expression):
     ```feel
     ="Our customer " + customerNo + " with orderId " + orderId +
     " contacted us with the following message: " + customerMessage +
     " Choose one of the following tools. Respond strictly with Tool_1,...Tool_2, etc.
     Separate multiple tools with a comma (no spaces).
     Tool_1: Get order status from logistics;
     Tool_2: Check inventory for replacement;
     Tool_3: Decide warranty eligibility;
     Tool_4: Draft customer reply;
     Tool_5: Ask human for approval;
     Tool_6: Create ticket for warehouse or support"
     ```
4. Set result variable: `toolChoice`

### Step 4: Add the Tool List Preparation Script

1. Add a **Script Task** named "Prepare Todo's"
2. Set the FEEL expression:
   ```feel
   =split(response, ",")
   ```
3. Result variable: `toolList`

### Step 5: Create the Ad-Hoc Subprocess

1. Add an **Ad-Hoc Subprocess**
2. Configure the `activeElementsCollection`:
   ```feel
   =toolList
   ```
3. Add the six tools inside (see implementations below)

### Step 6: Implement the Tools

#### Tool_1: Get Order Status (Script Task)
```feel
=["shipped", "order received", "being built"][floor(random number() * 3) + 1]
```
Result variable: `orderStatus`

#### Tool_2: Check Inventory (Script Task)
```feel
=random number() < 0.5
```
Result variable: `replacementAvailable`

#### Tool_3: Warranty Check (Script Task)
```feel
=random number() < 0.5
```
Result variable: `isEligableforWarranty`

#### Tool_4: Draft Reply (OpenAI Connector)
- Use the OpenAI connector with context from all gathered variables
- Prompt should include: `customerNo`, `orderId`, `customerMessage`, `orderStatus`, `replacementAvailable`, `isEligableforWarranty`, `ticketCreated`, `humanOpinion`
- Result variable: `email`

#### Tool_5: Human Approval (User Task)
- Link to a Camunda Form (`HumanOpinion.form`)
- Result variable: `humanOpinion`

#### Tool_6: Create Ticket (Script Task)
```feel
="TICKET-" + string(floor(random number() * 10000))
```
Result variable: `ticketCreated`

### Step 7: Add the Loop Decision

1. Add another **OpenAI Connector** task: "Decide if anything else needs to be done"
2. Include all context variables in the prompt
3. Add an **Exclusive Gateway** "Anything else required?"
4. Route back to "Prepare Todo's" if more tools are needed

### Step 8: Add Boundary Events 

#### Timer Boundary Event
Add a timer boundary event to the Ad-Hoc Subprocess for timeout handling:
```
PT30M
```
(30 minute timeout)

#### Message Boundary Event
Add a message boundary event to inject new customer information mid-process:
- Message name: `customerUpdate`
- Correlation key: `=customerNo`

### Step 9: Add Fallback Path

Add a **User Task** "Handle support case old-fashioned" that catches timeout or error scenarios.

---

## Testing the Process

1. **Start the infrastructure**:
   ```bash
   cd stack
   docker-compose up -d
   ```

2. **Deploy the process** via Camunda Modeler or the Operate UI

3. **Start a process instance** with sample variables:
   ```json
   {
     "customerNo": "CUST-12345",
     "orderId": "ORD-98765",
     "customerMessage": "My bike arrived damaged. The front wheel is bent and I need a replacement urgently."
   }
   ```

4. **Observe** the AI selecting appropriate tools in Operate

5. **Complete user tasks** in Tasklist when Tool_5 is triggered

## Troubleshooting

### AI returns unexpected tool format
Ensure your prompt strictly specifies the output format: `Tool_1,Tool_2` (no spaces).

### Ad-Hoc Subprocess doesn't execute tools
Verify that `toolList` is a proper list/array. Use the FEEL `split()` function correctly.

### OpenAI Connector fails
- Check that `OPENAI_API_KEY` secret is configured
- Verify the API key has sufficient quota
- Check network connectivity to `api.openai.com`

### User Task not appearing
Ensure the form reference is correct and the form file is deployed alongside the BPMN.
