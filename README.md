# Fraud Detection System

A Spring Boot backend that processes money transfers between bank accounts and runs every transfer through a set of fraud rules before any money moves.

I built this to practice the parts of backend work that actually matter in a banking context: keeping balances consistent, not processing the same payment twice, and leaving an audit trail that can't be edited later. The fraud rules are simple on purpose. What I cared about more was making it easy to add new ones without touching the transfer logic.

---

## What it does

- Creates bank accounts with an opening balance
- Transfers money between accounts
- Checks each transfer against fraud rules before it goes through
- Blocks suspicious transfers, and still records them with a fraud alert so they can be reviewed
- Writes an audit log entry for every transfer, successful or blocked
- Supports idempotency keys, so a client that retries the same request doesn't send the money twice
- Lists transactions with pagination, either all of them or per account

## How a transfer works

```
POST /api/transactions/{sender}/process
        │
        ├─ Idempotency key already used?  ──► return the original transaction
        │
        ├─ Sender / receiver exist?  ──► 400 if not
        ├─ Enough balance?           ──► 400 if not
        │
        ├─ Run fraud rules
        │     │
        │     └─ A rule fires ──► save transaction as FRAUD_SUSPECTED
        │                         create FraudAlert
        │                         write AuditLog
        │                         return 400 with the reason
        │
        └─ No rule fires ──► debit sender, credit receiver
                             save transaction as SUCCESS
                             write AuditLog
                             return the transaction
```

One detail I had to think about: when fraud is detected, the service throws an exception to stop the transfer. Normally that would roll back the whole database transaction, and the fraud alert and audit entry would disappear with it. The method is marked `@Transactional(noRollbackFor = FraudDetectedException.class)` so the balances stay untouched but the evidence of the attempt is kept.

## Fraud rules

Each rule is its own class implementing a small interface:

```java
public interface FraudRule {
    boolean isFraudulent(FraudCheckContext context);
    String getFraudReason();
}
```

Spring collects every `FraudRule` bean and injects them into `FraudDetectionEngine` as a list. Adding a new rule means writing one class and annotating it with `@Component`. The engine and the transaction service don't change.

Current rules:

| Rule | Fires when |
|------|-----------|
| `HighAmountFraudRule` | The amount is above 10,000 |
| `VelocityRule` | The sender already made 3 or more transfers in the last 5 minutes |
| `BlockedAccountRule` | The receiver is on the blocked list (currently one hardcoded demo account) |

The engine stops at the first rule that fires and returns its reason.

## Tech stack

- Java 17
- Spring Boot (Web MVC, Data JPA, Validation)
- Oracle Database (H2 is included for local testing)
- MapStruct for entity ↔ DTO mapping
- Lombok
- springdoc-openapi for Swagger UI
- Maven

## Project structure

```
src/main/java/com/frauddetectionsystem
├── Controller/     REST endpoints
├── Service/        Business logic (interfaces + impl/)
├── fraud/          Fraud engine and rules
├── model/          JPA entities: Account, Transaction, FraudAlert, AuditLog
├── repo/           Spring Data repositories
├── DTO/            Request / response objects
├── mapper/         MapStruct mappers
└── exception/      Custom exceptions and the global handler
```

## API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/accounts/add` | Create an account |
| POST | `/api/transactions/{senderAccountNumber}/process` | Transfer money |
| GET | `/api/transactions?page=0&size=10` | All transactions (paginated) |
| GET | `/api/transactions/{transactionId}` | One transaction by its UUID |
| GET | `/api/transactions/account/{accountNumber}` | Transactions sent or received by an account |

Swagger UI is available at `http://localhost:8080/swagger-ui/index.html` once the app is running.

### Example: create an account

```http
POST /api/accounts/add
Content-Type: application/json

{
  "accountNumber": "1001",
  "userName": "dina",
  "password": "secret123",
  "balance": 5000
}
```

### Example: transfer

```http
POST /api/transactions/1001/process
Content-Type: application/json

{
  "receiverAccountNumber": "1002",
  "amount": 250,
  "idempotencyKey": "a7f3c2e1-order-778"
}
```

Response:

```json
{
  "id": 1,
  "transactionId": "3f1c9b0e-7a52-4d8e-9f0a-2b6c1d4e5f60",
  "amount": 250.0,
  "transactionDate": "2026-09-26T18:05:12",
  "transactionStatus": "SUCCESS",
  "sender": "1001",
  "receiver": "1002"
}
```

If a fraud rule fires, you get a `400` with the reason, for example:

```
Fraud Detected: Amount exceeds the maximum allowed limit of 10,000!
```

## Running it locally

You need JDK 17 and an Oracle database (or switch the datasource to H2).

1. Clone the repo

   ```bash
   git clone https://github.com/Dina-Elmowafy/Fraud-Detection-System.git
   cd Fraud-Detection-System
   ```

2. Set your database connection in `src/main/resources/application.yaml`

   ```yaml
   spring:
     datasource:
       url: jdbc:oracle:thin:@localhost:1521/orclpdb
       username: your_user
       password: your_password
   ```

3. Run it

   ```bash
   ./mvnw spring-boot:run
   ```

Hibernate creates the tables on startup (`ddl-auto: update`).

## What's next

This is still a work in progress. Things I'm planning to add:

- **Security**: Spring Security with JWT, password hashing with BCrypt, and making sure users can only transfer from their own accounts
- **Tests**: unit tests for each fraud rule and integration tests for the transfer flow
- **Concurrency**: locking on account balances so two transfers hitting the same account at the same moment can't corrupt the balance
- **Configurable rules**: move thresholds (amount limit, velocity window) to configuration and keep the blocked-account list in the database
- **Fraud review endpoints**: list open fraud alerts and let an admin resolve them
- **Docker Compose** setup with the database so the project runs with one command
