# Exercise 1: BPMN Process Testing (Kotlin)

## Task

Implement 6 test methods in `BikeSubscriptionProcessTest.kt` following the pattern from `NewsletterSubscriptionProcessTest.kt`.

## Reference Implementation

Study `NewsletterSubscriptionProcessTest.kt` to understand:
- Test setup with mocked use cases
- Process assertions with CamundaAssert
- Timer testing with processTestContext.increaseTime()
- Message sending and correlation
- Worker verification with MockK

## Bike Subscription Process Flow

1. **Check bike availability**
2. If not available: send rejection → end
3. If available: send confirmation → wait for payment
4. **Payment reminder** every 3 days (non-interrupting timer)
5. Handle **cancelation message** (interrupting)
6. After payment: **ship bike** → wait for delivery
7. Send **welcome mail** → end

## Test Scenarios

### 1. Happy Path
Start process → verify availability check → send payment → send delivery confirmation → verify welcome mail

### 2. Not Available
Mock availability as false → start process → verify rejection mail → process ends

### 3. Cancelation
Start process → send cancel message → verify cancelation notification → process ends

### 4. Timer - Payment Reminder
Start process → advance time 72h → verify reminder sent → advance 72h again → verify reminder sent twice

### 5. Reminder Then Payment
Start process → trigger reminder → send payment → complete flow → verify success

### 6. Complete Integration
Full flow with all verifications and confirmVerified()

## Testing Tools

```kotlin
// Start process
val instanceKey = processPort.startSubscription(subscriptionId, bikeId)

// Check status
CamundaAssert.assertThatProcessInstance(byKey(instanceKey)).isActive()

// Advance time
processTestContext.increaseTime(Duration.ofHours(72))

// Send messages
processPort.sendPaymentReceived(subscriptionId)
processPort.sendRequestCanceled(subscriptionId)
processPort.sendBikeReceived(subscriptionId)

// Verify workers
verify { useCase.method(subscriptionId) }
verify(exactly = 2) { useCase.method(any()) }

// Verify completion
CamundaAssert.assertThatProcessInstance(instance).isCompleted()
CamundaAssert.assertThatProcessInstance(instance).hasCompletedElement(elementId, count)
```

## Run Tests

```bash
../../gradlew test
```

Expected: 2 newsletter tests pass, 6 bike tests fail with "Implement this test"

Your goal: Make all bike tests pass! 🎯
