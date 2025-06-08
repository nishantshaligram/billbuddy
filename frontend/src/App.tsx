import './App.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import {useDispatch} from 'react-redux';

import LoginPage from './features/auth/LoginPage';
import RegisterPage from './features/auth/RegisterPage';
import ProtectedLayout from './layouts/ProtectedLayout';
import { useEffect } from 'react';
import { getCurrentUser } from './services/authService';
import { setUser } from './features/auth/authSlice';
import { store } from './store';

function DashboardPage() {
  console.log( 'user', store.getState().auth );
  return <div>Welcome to the Dashboard!</div>;
}

function App() {
  const dispatch = useDispatch();

  useEffect(() => {
    const fetchUser = async () => {
      console.log( 'user', store.getState().auth );
      const userID = store.getState().auth.user?.id;
      const user = await getCurrentUser( userID );
      if (user) dispatch(setUser(user));
    };
    fetchUser();
  }, [dispatch]);
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
