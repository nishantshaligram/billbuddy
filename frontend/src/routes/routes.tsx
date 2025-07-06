import { Routes, Route } from 'react-router-dom';
import LoginPage from '../features/auth/LoginPage';
import RegisterPage from '../features/auth/RegisterPage';
import ProtectedLayout from '../layouts/ProtectedLayout';
import ExpenseListPage from '../features/expense/ExpenseListPage';
import ExpenseDetailPage from '../features/expense/ExpenseDetailPage';
import ExpenseFormPage from '../features/expense/ExpenseFormPage';

function DashboardPage() {
  return <div>Welcome to the Dashboard!</div>;
}

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />
      <Route path="/" element={<ProtectedLayout />}>
        <Route path="dashboard" element={<DashboardPage />} />
        <Route path="expenses" element={<ExpenseListPage />} />
        <Route path="expenses/new" element={<ExpenseFormPage />} />
        <Route path="expenses/:id" element={<ExpenseDetailPage />} />
        {/* Add other protected pages here */}
      </Route>
    </Routes>
  );
}