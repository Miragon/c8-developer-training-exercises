# Camunda 8 Developer Training - BPMN Testing Exercises

This repository contains hands-on exercises for learning BPMN process testing with Camunda 8.

## 📚 Structure

```
c8-developer-training-exercises/
├── exercises/
│   ├── exercise-1-testing-kotlin/  # Kotlin version (ready to use)
│   └── exercise-1-testing-java/    # Java version (requires conversion)
├── stack/                          # Docker infrastructure
└── README.md                       # This file
```

## 🎯 Exercise Overview

Each exercise contains two complete process implementations:

### Newsletter Subscription Process (Reference Implementation)
**Status**: ✅ Fully implemented with tests

A complete example showing:
- Hexagonal architecture pattern
- BPMN process automation
- Job workers for service tasks
- Message correlation
- Timer events
- Process testing with @CamundaProcessTest

**Study this implementation to understand the patterns!**

### Bike Subscription Process (Training Exercise)
**Status**: ⚠️ Implementation complete, tests to be implemented

Your task:
- Implementation is provided as reference
- 6 empty test methods need to be completed
- Follow the newsletter process test pattern
- Learn BPMN testing hands-on

## 🚀 Quick Start

### 1. Start Infrastructure

```bash
cd stack
docker-compose up -d
```

This starts:
- PostgreSQL database
- Camunda Platform 8.8 (Zeebe + Operate + Tasklist)
- Elasticsearch

### 2. Run the Exercise (Kotlin Version)

```bash
cd exercises/exercise-1-testing-kotlin
../../gradlew bootRun
```

Application runs on: http://localhost:8081

### 3. Run Tests

```bash
../../gradlew test
```

Expected results:
- Newsletter tests (2): ✅ All passing
- Bike tests (6): ❌ Failing with "Implement this test"

### 4. Complete the Exercise

Open `BikeSubscriptionProcessTest.kt` and implement the 6 test methods!

## 🔗 Access Points

- **Backend API**: http://localhost:8081
- **Camunda Web UI** (Operate/Tasklist): http://localhost:8080 (demo/demo)
- **PostgreSQL**: localhost:5432 (admin/admin)

## 🎓 Learning Objectives

1. **BPMN Process Testing** - Using @CamundaProcessTest
2. **Timer Testing** - Advancing time, testing timers
3. **Message Correlation** - Sending messages to processes
4. **Process Assertions** - Using CamundaAssert
5. **Hexagonal Architecture** - Understanding the pattern

## 📋 Test Scenarios to Implement

1. ✅ Happy path (availability → payment → delivery → welcome)
2. ✅ Not available path
3. ✅ Cancelation during payment
4. ✅ Timer: 3-day reminder
5. ✅ Multiple reminders then payment
6. ✅ Full integration test

## 🤝 Java Version

Available in `exercises/exercise-1-testing-java/` - requires conversion from Kotlin.

See `exercises/exercise-1-testing-java/CONVERSION-GUIDE.md` for instructions.

---

**Ready to start?** Open `BikeSubscriptionProcessTest.kt` and implement the tests! 🚀
