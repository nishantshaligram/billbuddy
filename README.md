# **Splitwise-Like Application - Architecture & Development Plan**

## **1. Overview**
This document outlines the architecture and development plan for a **Splitwise-like expense-sharing application** using **Java Spring Boot** for the backend, **React.js** for the frontend, and **Docker** for deployment. The codebase is structured as a **monorepo**, with each microservice deployed independently.

---

## **2. High-Level Architecture**
The application follows a microservices-based architecture, consisting of independent services that communicate via REST or event-driven messaging.

### **Core Components**
1. **Frontend (React.js)**
   - Developed using **React.js** with **Vite** for fast builds
   - **Material-UI / Tailwind CSS** for modern UI components
   - **React Router** for navigation
   - **Redux Toolkit** for state management
   - **React Query** for efficient data fetching
   - **Axios** for API communication
   - **JWT** for authentication
   - **TypeScript** for type safety

2. **Backend Microservices (Spring Boot)**
   - **User Service** - User management & authentication
   - **Group Service** - Expense group management
   - **Expense Service** - Expense creation & splitting
   - **Settlement Service** - Balance tracking & settlements
   - **Notification Service** - Real-time notifications
   - **Payment Service** - Payment processing
   - **API Gateway** - Service routing & load balancing

3. **Database Architecture**
   - **PostgreSQL 15** for structured data:
     - User profiles
     - Groups
     - Expenses
     - Settlements
   - **MongoDB 6.0** for unstructured data:
     - Activity logs
     - Notifications
     - Analytics data
     - Cached data

4. **Service Communication**
   - **REST APIs** for synchronous communication
   - **RabbitMQ** for event-driven messaging
   - **Service Discovery** using Eureka
   - **Circuit Breaker** using Resilience4j

---

## **3. Monorepo Directory Structure**
```
splitwise/
├── frontend/                    # React.js application
│   ├── src/
│   ├── public/
│   └── package.json
├── backend/                     # Spring Boot microservices
│   ├── services/
│   │   ├── user-service/       # User management
│   │   ├── group-service/      # Group management
│   │   ├── expense-service/    # Expense handling
│   │   ├── settlement-service/ # Settlement processing
│   │   ├── notification-service/ # Notifications
│   │   ├── payment-service/    # Payment processing
│   │   └── api-gateway/        # API Gateway
│   └── pom.xml                 # Parent POM
├── common/                      # Shared utilities
│   ├── models/                 # Shared DTOs
│   └── utils/                  # Common utilities
├── deployment/                  # Docker & K8s configs
│   ├── docker/                 # Dockerfiles
│   └── kubernetes/             # K8s manifests
└── scripts/                    # Build & deployment scripts
```

---

## **4. Technology Stack Details**

### **Frontend Stack**
- **React.js** with Vite
- **TypeScript**
- **Material-UI / Tailwind CSS**
- **Redux Toolkit**
- **React Query**
- **Axios**
- **JWT Authentication**
- **React Router v6**
- **ESLint & Prettier**

### **Backend Stack**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **Spring Cloud**
- **Spring AMQP** (RabbitMQ)
- **Resilience4j**
- **Lombok**
- **MapStruct**
- **JUnit 5 & Mockito**

### **Database Stack**
- **PostgreSQL 15** for structured data
- **MongoDB 6.0** for unstructured data
- **Redis** for caching

### **DevOps Stack**
- **Docker**
- **Docker Compose**
- **GitHub Actions** for CI/CD
- **SonarQube** for code quality
- **Prometheus & Grafana** for monitoring

---

## **5. Database Schema Design**

### **PostgreSQL Schemas**

1. **User Service**
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT email_check CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

CREATE INDEX idx_users_email ON users(email);
```

2. **Group Service**
```sql
CREATE TABLE groups (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_by BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT name_length CHECK (char_length(name) >= 3)
);

CREATE TABLE group_members (
    id BIGSERIAL PRIMARY KEY,
    group_id BIGINT NOT NULL REFERENCES groups(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    joined_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(group_id, user_id)
);

CREATE INDEX idx_group_members_group_id ON group_members(group_id);
CREATE INDEX idx_group_members_user_id ON group_members(user_id);
```

3. **Expense Service**
```sql
CREATE TYPE split_type AS ENUM ('EQUAL', 'PERCENTAGE', 'EXACT_AMOUNTS', 'SHARES');

CREATE TABLE expenses (
    id BIGSERIAL PRIMARY KEY,
    group_id BIGINT NOT NULL REFERENCES groups(id) ON DELETE CASCADE,
    description TEXT NOT NULL,
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    paid_by BIGINT NOT NULL REFERENCES users(id),
    split_type split_type NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    metadata JSONB DEFAULT '{}'::jsonb
);

CREATE TABLE expense_splits (
    id BIGSERIAL PRIMARY KEY,
    expense_id BIGINT NOT NULL REFERENCES expenses(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    amount_owed DECIMAL(10,2) NOT NULL,
    percentage DECIMAL(5,2),
    shares INTEGER,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(expense_id, user_id)
);

CREATE INDEX idx_expenses_group_id ON expenses(group_id);
CREATE INDEX idx_expenses_paid_by ON expenses(paid_by);
CREATE INDEX idx_expense_splits_expense_id ON expense_splits(expense_id);
CREATE INDEX idx_expense_splits_user_id ON expense_splits(user_id);
```

4. **Settlement Service**
```sql
CREATE TYPE settlement_status AS ENUM ('PENDING', 'COMPLETED', 'CANCELLED');

CREATE TABLE settlements (
    id BIGSERIAL PRIMARY KEY,
    payer_id BIGINT NOT NULL REFERENCES users(id),
    payee_id BIGINT NOT NULL REFERENCES users(id),
    amount DECIMAL(10,2) NOT NULL CHECK (amount > 0),
    status settlement_status NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT different_users CHECK (payer_id != payee_id)
);

CREATE INDEX idx_settlements_payer_id ON settlements(payer_id);
CREATE INDEX idx_settlements_payee_id ON settlements(payee_id);
CREATE INDEX idx_settlements_status ON settlements(status);
```

### **MongoDB Collections**

1. **Notification Service**
```javascript
{
  _id: ObjectId,
  userId: Number,
  type: String,
  message: String,
  read: Boolean,
  createdAt: Date,
  metadata: Object
}
```

2. **Activity Logs**
```javascript
{
  _id: ObjectId,
  userId: Number,
  action: String,
  entityType: String,
  entityId: Number,
  timestamp: Date,
  details: Object
}
```

---

## **6. Docker Deployment**

### **Docker Compose Configuration**
```yaml
version: '3.8'

services:
  frontend:
    build: ./frontend
    ports:
      - "3000:3000"
    environment:
      - REACT_APP_API_URL=http://api-gateway:8080

  api-gateway:
    build: ./backend/services/api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - user-service
      - group-service
      - expense-service
      - settlement-service
      - notification-service
      - payment-service

  user-service:
    build: ./backend/services/user-service
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/user_db
      - SPRING_RABBITMQ_HOST=rabbitmq

  # Other services...

  postgres:
    image: postgres:15-alpine
    environment:
      - POSTGRES_USER=splitwise
      - POSTGRES_PASSWORD=splitwise
      - POSTGRES_DB=splitwise
    volumes:
      - postgres_data:/var/lib/postgresql/data
    ports:
      - "5432:5432"
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U splitwise"]
      interval: 5s
      timeout: 5s
      retries: 5

  mongodb:
    image: mongo:6.0
    volumes:
      - mongodb_data:/data/db

  rabbitmq:
    image: rabbitmq:3-management
    ports:
      - "5672:5672"
      - "15672:15672"

volumes:
  postgres_data:
  mongodb_data:
```

---

## **7. Security & Authentication**

1. **JWT Authentication**
   - Access tokens for API authentication
   - Refresh tokens for session management
   - Token blacklisting for logout

2. **API Security**
   - Rate limiting
   - CORS configuration
   - Input validation
   - SQL injection prevention
   - XSS protection

3. **Data Security**
   - Password hashing with BCrypt
   - HTTPS/TLS encryption
   - Data encryption at rest
   - Regular security audits

---

## **8. Monitoring & Logging**

1. **Application Monitoring**
   - Prometheus metrics
   - Grafana dashboards
   - Health check endpoints
   - Performance monitoring

2. **Logging**
   - Centralized logging with ELK Stack
   - Log aggregation
   - Error tracking
   - Audit logging

---

## **9. Development Workflow**

1. **Code Quality**
   - SonarQube analysis
   - Unit test coverage
   - Integration tests
   - Code review process

2. **CI/CD Pipeline**
   - Automated builds
   - Test automation
   - Docker image building
   - Deployment automation

3. **Version Control**
   - Git flow branching strategy
   - Semantic versioning
   - Automated changelog generation
   - Release management

---

This architecture provides a **scalable, maintainable, and secure** foundation for the Splitwise-like application. 🚀

