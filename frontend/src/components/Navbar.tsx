import { useDispatch } from 'react-redux';
import { logout } from '../features/auth/authSlice';
import { Button } from '@/components/ui/button';

function Navbar() {
  const dispatch = useDispatch();

  const handleLogout = () => {
    dispatch(logout());
  };

  return (
    <header className="h-16 border-b px-6 flex items-center justify-between bg-white shadow-sm">
      <div className="font-semibold text-lg tracking-tight text-primary">Dashboard</div>
      <Button variant="outline" onClick={handleLogout}>
        Logout
      </Button>
    </header>
  );
}

export default Navbar;
