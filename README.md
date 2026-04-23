# 🎓 Camunda 8 Developer Training Exercises

Hands-on exercises for our Zeebe training program.

## 📚 Exercises

1. **Exercise 1: BPMN Testing** (Kotlin & Java)
   - Learn process testing with Camunda 8
   - Path: `exercises/exercise-1-testing-{kotlin|java}`

2. **Exercise 2: Outbox & Idempotency** (Kotlin & Java)
   - Master distributed transaction patterns
   - Path: `exercises/exercise-2-outbox-{kotlin|java}`

3. **Exercise 3: Outbound Connector** (Java)
   - Build a custom Camunda 8 outbound connector
   - Path: `exercises/exercise-3-connector-java`

4. **Exercise 4: Agentic Orchestration**
   - AI-powered decision making with process orchestration
   - Path: `exercises/exercise-4-agentic-orchestration`

## 🚀 Quick Start

1. Start infrastructure: `cd stack && docker-compose up -d`
2. Choose your exercise and follow its README
3. Access Camunda UI at http://localhost:8080 (demo/demo)

## 📁 Repository Structure

```
exercises/
├── exercise-1-testing-java/      # BPMN process testing (Java)
├── exercise-1-testing-kotlin/    # BPMN process testing (Kotlin)
├── exercise-2-outbox-java/       # Outbox & idempotency patterns (Java)
├── exercise-2-outbox-kotlin/     # Outbox & idempotency patterns (Kotlin)
├── exercise-3-connector-java/    # Custom outbound connector (Java)
└── exercise-4-agentic-orchestration/  # AI-powered orchestration
stack/                            # Docker Compose infrastructure
bruno/                            # API test collection
```

## 🛠️ Tech Stack

- **Java 21** & **Kotlin**
- **Spring Boot 3.4.1**
- **Camunda 8** (Zeebe 8.8.2)
- **PostgreSQL 17.5** for persistence
- **H2** for test database
- **Maven** for build management
