import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import LoginPage from './features/auth/LoginPage';
import RegisterPage from './features/auth/RegisterPage';
import ProtectedLayout from './layouts/ProtectedLayout';

function DashboardPage() {
  return <div>Welcome to the Dashboard!</div>;
}

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        
        <Route path="/" element={<ProtectedLayout />}>
          <Route path="dashboard" element={<DashboardPage />} />
          {/* Add other protected pages here */}
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
