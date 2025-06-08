import { Link, useLocation } from 'react-router-dom';
import { cn } from '@/lib/utils';
import { Home, Users, Activity, Settings } from 'lucide-react';

const navItems = [
  { label: 'Dashboard', to: '/dashboard', icon: <Home className="w-4 h-4 mr-2" /> },
  { label: 'Groups', to: '/groups', icon: <Users className="w-4 h-4 mr-2" /> },
  { label: 'Activity', to: '/activity', icon: <Activity className="w-4 h-4 mr-2" /> },
  { label: 'Settings', to: '/settings', icon: <Settings className="w-4 h-4 mr-2" /> },
];

function Sidebar() {
  const location = useLocation();

  return (
    <aside className="w-64 bg-white p-4 border-r shadow-sm hidden md:block">
      <div className="text-2xl font-bold mb-8 tracking-tight text-primary">BillBuddy</div>
      <nav className="space-y-2">
        {navItems.map((item) => (
          <Link
            key={item.to}
            to={item.to}
            className={cn(
              'flex items-center px-4 py-2 rounded-lg transition-colors hover:bg-muted text-base',
              location.pathname === item.to
                ? 'bg-muted font-semibold text-primary'
                : 'text-muted-foreground'
            )}
          >
            {item.icon}
            {item.label}
          </Link>
        ))}
      </nav>
    </aside>
  );
}

export default Sidebar;