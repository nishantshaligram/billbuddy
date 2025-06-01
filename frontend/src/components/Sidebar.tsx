import { Link, useLocation } from 'react-router-dom';
import { cn } from '@/lib/utils';

const navItems = [
  { label: 'Dashboard', to: '/dashboard' },
  { label: 'Groups', to: '/groups' },
  { label: 'Activity', to: '/activity' },
  { label: 'Settings', to: '/settings' },
];

function Sidebar() {
  const location = useLocation();

  return (
    <aside className="w-64 bg-gray-100 p-4 border-r hidden md:block">
      <div className="text-2xl font-bold mb-6">BillBuddy</div>
      <nav className="space-y-2">
        {navItems.map((item) => (
          <Link
            key={item.to}
            to={item.to}
            className={cn(
              'block px-3 py-2 rounded hover:bg-gray-200',
              location.pathname === item.to ? 'bg-gray-300 font-semibold' : ''
            )}
          >
            {item.label}
          </Link>
        ))}
      </nav>
    </aside>
  );
}

export default Sidebar;