# EzMail - Architecture Overview

## Overview
EzMail is a collection of Spring Boot microservices that allows users to send emails via API. It includes authentication, email tracking, billing, and API usage monitoring. The system is containerized using Docker and features monitoring with Grafana and Prometheus.

## Architecture Diagram
```
+----------------+        +----------------+        +----------------+
|  API Gateway  | -----> |  User Service  | -----> |  Billing       |
+----------------+        +----------------+        +----------------+
       |                          |                          |
       v                          v                          v
+----------------+        +----------------+        +----------------+
|  Email Service | -----> |  Monitoring    | -----> |  Registry      |
+----------------+        +----------------+        +----------------+
```

All microservices share a centralized PostgreSQL database instance, with each service using its own schema for data isolation.

## Microservices

1. **User Service** (Manages user accounts, API token usage, and account details)
2. **Email Service** (Processes and sends emails, tracks sent mail, and logs data)
3. **Billing Service** (Manages different usage tiers, tracks API usage, and processes payments)
4. **Monitoring Service** (Includes Grafana and Prometheus for observability)
5. **Registry Service** (Eureka-based service discovery)
6. **Gateway Service** (Manages API authentication and routing)

## Infrastructure Components

- **Eureka**: Service discovery for microservices
- **API Gateway**: Central entry point for managing authentication and routing
- **Prometheus**: Collects metrics for monitoring
- **Grafana**: Provides visualization and dashboards for performance tracking
- **Docker**: Containerization for deploying microservices
- **Centralized PostgreSQL Database**: A single PostgreSQL instance running in Docker, with each microservice using its own schema for data separation and security

## Example Endpoints

### User Service
```http
GET /user
```
**Description:** Get user details (Requires JWT)

```http
GET /user/token
```
**Description:** Retrieve API token (Requires JWT)

### Email Service
```http
POST /email/send?token=<api-token>
```
**Description:** Sends an email (Requires API Token)
```json
{
  "to": "recipient@example.com",
  "subject": "Hello",
  "body": "This is a test email"
}
```

### Billing Service
```http
GET /billing/usage
```
**Description:** Retrieves the current usage and plan details (Requires JWT)

```http
POST /billing/upgrade
```
**Description:** Upgrade the user's plan (Requires JWT)
```json
{
  "plan": "premium"
}
```

## Monitoring

- **Prometheus Metrics Endpoint**: `/actuator/prometheus`
- **Grafana Dashboard**: Monitors API requests, response times, failures, and billing metrics

## Deployment

1. Build microservices using Maven
2. Create Docker images
3. Deploy using Docker Compose or Kubernetes
4. Deploy a centralized PostgreSQL instance in Docker
5. Configure Prometheus and Grafana for monitoring

---
This document serves as an initial reference for EzMail's architecture. More details on implementation and deployment will be added as the project evolves.
