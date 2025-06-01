import { useDispatch } from 'react-redux';
import { logout } from '../features/auth/authSlice';

function Navbar() {
  const dispatch = useDispatch();

  const handleLogout = () => {
    dispatch(logout());
  };

  return (
    <header className="h-16 border-b px-4 flex items-center justify-between bg-white shadow-sm">
      <div className="font-semibold">Dashboard</div>
      <button
        onClick={handleLogout}
        className="text-sm px-3 py-1 rounded bg-red-500 text-white hover:bg-red-600"
      >
        Logout
      </button>
    </header>
  );
}

export default Navbar;
