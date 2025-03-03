# EzMail - Microservice-Based Email Service

EzMail is a microservice-based email-sending service similar to EmailJS. It provides an API for sending emails, managing users, handling billing, and monitoring system performance.

## **Architecture**
EzMail consists of multiple microservices, each handling a specific function. These services interact with a central PostgreSQL database.

### **Microservices**
1. **User Service** - Manages user authentication and profiles.
2. **Email Service** - Handles email sending and logging.
3. **Billing Service** - Tracks usage and plan subscriptions.
4. **Gateway Service** - API Gateway to route requests.
5. **Registry Service** - Service discovery using Eureka.
6. **Monitoring Service** - Tracks API usage via Grafana + Prometheus.

## **Flow Overview**
1. **User Signup & Authentication**
   - Users register and get a **non-expiring API token**.
   - JWT + refresh tokens handle authentication.
   
2. **Sending Emails**
   - Users send emails via the API Gateway.
   - The Email Service processes and logs the request.

3. **Billing & Usage Tracking**
   - Billing Service monitors emails sent against plan limits.
   - Users can upgrade plans via API.

4. **Monitoring & Logs**
   - API calls and errors are logged using Grafana + Prometheus.

---

## **Microservices & Endpoints**
### **1. User Service (Authentication & Account Management)**
#### **Entity: User**
| Field      | Type    |
|-----------|--------|
| id        | UUID   |
| email     | String (Unique) |
| password  | String (Hashed) |
| sendAs    | String |
| plan      | Enum (FREE, BASIC, PRO) |
| apiToken  | String (Non-expiring) |
| createdAt | Timestamp |

#### **Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/user/signup` | Register a new user |
| **POST** | `/user/login` | Authenticate user, returns JWT & refresh token |
| **POST** | `/user/refresh-token` | Generate a new JWT using refresh token |
| **GET**  | `/user/profile` | Get user details (requires JWT) |
| **PATCH** | `/user/update` | Update user profile |
| **POST** | `/user/logout` | Revoke refresh token |

### **2. Email Service (Handles Sending Emails & Logs)**
#### **Entity: Email**
| Field    | Type        |
|---------|------------|
| id      | UUID       |
| userId  | UUID (FK to User) |
| from    | String     |
| to      | String     |
| subject | String     |
| body    | Text       |
| status  | Enum (SENT, FAILED, PENDING) |
| sentAt  | Timestamp |

#### **Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| **POST** | `/email/send` | Send an email (Requires API Token or JWT) |
| **GET** | `/email/logs` | Get all sent emails for the user |
| **GET** | `/email/logs/{id}` | Get details of a specific email |
| **GET** | `/email/usage` | Get email sending usage statistics |

### **3. Billing Service (Handles Plans & Usage)**
#### **Entity: Billing**
| Field       | Type        |
|------------|------------|
| id         | UUID       |
| userId     | UUID (FK to User) |
| plan       | Enum (FREE, BASIC, PRO) |
| emailsSent | Integer    |
| maxLimit   | Integer    |
| renewalDate| Timestamp  |

#### **Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| **GET** | `/billing/usage` | Get email sending usage statistics |
| **GET** | `/billing/plan` | Get current plan details |
| **POST** | `/billing/upgrade` | Upgrade to a new plan |
| **POST** | `/billing/webhook` | Handle payments via webhook |

### **4. Gateway Service (API Gateway)**
Handles authentication and forwards requests to respective microservices.

#### **Endpoints**
| Method | Endpoint | Forwards To |
|--------|----------|-------------|
| **POST** | `/api/user/signup` | `/user/signup` |
| **POST** | `/api/user/login` | `/user/login` |
| **GET** | `/api/email/logs` | `/email/logs` |
| **POST** | `/api/email/send` | `/email/send` |
| **GET** | `/api/billing/usage` | `/billing/usage` |

### **5. Registry Service (Eureka)**
Handles service discovery.

#### **Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| **GET** | `/eureka/apps` | Get all registered services |

### **6. Monitoring Service (Grafana + Prometheus)**
Tracks system metrics and performance.

#### **Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| **GET** | `/monitoring/metrics` | Get system metrics |
| **GET** | `/monitoring/logs` | Get application logs |

---

## **Folder Structure**
Each microservice follows this structure:
```
microservice-name/
│── controller/        # API Endpoints (Routes)
│── service/           # Business Logic
│   ├── Service.java
│   ├── ServiceImpl.java
│── entity/            # Database Models (Beans)
│── repository/        # Database Access Layer
│── config/            # Configuration Files (JWT, DB)
│── main.java          # Entry Point
```

---

## **Security & Authentication**
- **User Login** returns **JWT** & **Refresh Token**.
- **JWT** is used for authentication (expires every X minutes).
- **Refresh Token** allows getting a new JWT without logging in.
- **API Token (non-expiring)** is used for email sending.
- API Gateway validates JWT before forwarding requests.

---

## **Tech Stack**
- **Backend:** Spring Boot (Java)
- **Database:** PostgreSQL
- **Auth:** JWT & Refresh Tokens
- **API Gateway:** Spring Cloud Gateway
- **Service Registry:** Eureka
- **Monitoring:** Grafana + Prometheus
- **Queue (Optional for async email processing):** RabbitMQ or Kafka

---

## **Flow Summary**
1. User registers → Gets API token & JWT.
2. User logs in → Gets JWT & Refresh token.
3. User sends email → API Gateway forwards to Email Service.
4. Email Service sends mail → Logs email & updates Billing.
5. Billing tracks usage → Prevents exceeding limits.
6. Monitoring tracks API calls → Alerts on failures.

---

## **Contributions & Issues**
For feature requests, issues, or contributions, open a pull request or create an issue.

---

## 📜 License

This project is licensed under the GNU General Public License v3 - see the [LICENSE](LICENSE.md) file for details.

## 💡 Author

Created by **[Nnisarg Gada](https://nnisarg.in)**. Feel free to reach via [contact@nnisarg.in](mailto:contact@nnisarg.in)!
