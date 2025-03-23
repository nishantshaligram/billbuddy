# **BillBuddy Application - Architecture & Development Plan**

## **1. Overview**

This document outlines the architecture and development plan for a **Bill expense-sharing application** using **Java Spring Boot** for the backend, **React.js** for the frontend, and **Docker** for deployment. The codebase is structured as a **monorepo**, with each microservice deployed independently.

---

## **2. High-Level Architecture**

The application follows a microservices-based architecture, consisting of independent services that communicate via REST APIs and event-driven messaging. The frontend is developed using React.js, interacting with the backend via an API Gateway.

### **Core Components**

1. **Frontend (React.js)**

   - Developed using **React.js** with **Vite** for fast builds
   - **Shadcn** for UI Components
   - **Tailwind CSS** for modern UI components
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

4. **Service Communication**
   - **REST APIs** for synchronous communication
   - **Kafka** for event-driven messaging

---
