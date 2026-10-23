# Fraud Detection System

A Java 17 and Spring Boot REST API for account transfers. It applies fraud rules before changing balances, records blocked attempts, and keeps an audit entry for successful and rejected transfers.

## What it demonstrates

- Account creation and paginated transaction queries
- Transfers with balance and account-state checks
- Fraud rules for high-value transfers, transfer velocity, and a blocked demo account
- Idempotency keys scoped to the sender
- Audit records for successful transfers and fraud alerts for blocked attempts
- Pessimistic write locks on the sender and receiver account rows during a transfer

The fraud rules implement `FraudRule` and are collected by `FraudDetectionEngine`. A matching rule stops the transfer. Fraud attempts are saved with a `FRAUD_SUSPECTED` status and an alert before the service raises an exception; `noRollbackFor` keeps that evidence while leaving balances unchanged.

## Fraud rules

| Rule | Current behavior |
| --- | --- |
| `HighAmountFraudRule` | Blocks amounts greater than 10,000 |
| `VelocityRule` | Blocks a sender after 3 or more successful transfers in the previous 5 minutes |
| `BlockedAccountRule` | Blocks demo receiver account `111` |

The blocked account and threshold values are examples and are currently hard-coded.

## API

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/accounts/add` | Create an account |
| POST | `/api/transactions/{senderAccountNumber}/process` | Process a transfer |
| GET | `/api/transactions?page=0&size=10` | List transactions |
| GET | `/api/transactions/{transactionId}` | Get a transaction |
| GET | `/api/transactions/account/{accountNumber}` | List transactions sent or received by an account |

Swagger UI is available at `http://localhost:8080/swagger-ui/index.html`.

Example transfer request:

```http
POST /api/transactions/1001/process
Content-Type: application/json

{
  "receiverAccountNumber": "1002",
  "amount": 250,
  "idempotencyKey": "order-778"
}
```

## Run locally

Requirements: JDK 17.

The default profile uses an in-memory H2 database, so no database server or credentials are needed for a local run:

```bash
./mvnw spring-boot:run
```

To use Oracle instead, set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` in the environment before starting the app. For example, set `DB_URL` to your Oracle JDBC URL. Database settings are not stored in the repository.

## Tests

Run the test suite with:

```bash
./mvnw test
```

The tests cover high-amount boundaries, blocked-account matching, and the velocity threshold. A Spring context-load test is also included.

## Current limitations

This is a learning project and is not suitable for real financial data. It does not yet provide authentication or authorization, and account password handling is not production-safe. Fraud thresholds and the blocked-account list are hard-coded. The account locks protect concurrent balance updates at the database level, but the transfer flow still needs dedicated integration tests for concurrent and rollback scenarios.
