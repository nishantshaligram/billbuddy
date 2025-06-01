# 💸 BillBuddy – Frontend

BillBuddy is a modern expense-sharing application (inspired by Splitwise) that helps users track shared bills and group expenses. This is the frontend of the application, built using a modern React stack with a focus on clean architecture, scalable state management, and secure authentication.

---

## 🧱 Tech Stack

- **React** (with Vite)
- **React Router** for routing
- **Redux Toolkit** for global state
- **React Query** for fetching/caching data
- **Axios** for API handling
- **JWT (HttpOnly cookies)** for secure authentication
- **Tailwind CSS** + **shadcn/ui** for styling and components
- **Jest** for testing

---

## 📁 Folder Structure

<pre>
src/
│
├── features/ # Feature-based structure
│ ├── auth/ # Login/Register and auth slice
│ └── groups/ # Group listing, hooks, components
│
├── layouts/ # Layout components like ProtectedLayout
├── lib/ # Axios instance, utility functions
├── pages/ # Routed views like Dashboard, Login
├── hooks/ # Custom hooks
└── store/ # Redux store config
</pre>

---

## 🔐 Authentication

- JWT tokens are sent as **HttpOnly cookies** (secure and not accessible via JS).
- No token is stored in localStorage.
- The `/auth/me` endpoint is used to persist session on page reload.
- Auth state is synced via React Query and Redux.

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-org/billbuddy-frontend.git
cd billbuddy-frontend
```

### 2. Install dependencies

```bash
npm install
```

### 3. Create .env file

VITE_API_BASE_URL=http://localhost:8080

```bash
npm run dev
```

Note: Make sure your backend Spring Boot services are running at the appropriate port and configured with CORS and cookie-based JWT auth.

## ✅ Implemented Features
- Secure cookie-based JWT auth (Login, Register, /auth/me)
- Global auth state management (Redux)
- Group list fetched with React Query
- Protected routes
- Basic responsive layout using shadcn + Tailwind

## 🛣️ Next Up
- Create Group
- View Group Details + Expenses
- Add Expense
- Settle Up
- User Settings & Profile
- Group Invitations

## 📄 License
GNUv3 Nishant Shaligram