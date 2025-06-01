import { Navigate, Outlet } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { type RootState } from '../store';
import Sidebar from '../components/Sidebar';
import Navbar from '../components/Navbar';

function ProtectedLayout() {
  const token = useSelector((state: RootState) => state.auth.token);

  if (!token) return <Navigate to="/login" />;

  return (
    <div className="flex h-screen">
      <Sidebar />
      <div className="flex flex-col flex-1">
        <Navbar />
        <main className="p-4 overflow-y-auto">
          <Outlet />
        </main>
      </div>
    </div>
  );
}

export default ProtectedLayout;