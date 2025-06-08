import { useForm } from 'react-hook-form';
import { useDispatch } from 'react-redux';
import { loginSuccess } from './authSlice';
import { login } from '../../services/authService';
import { useNavigate } from 'react-router-dom';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';

function LoginPage() {
  const { register, handleSubmit } = useForm();
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const onSubmit = async (data: any) => {
    try {
      const result = await login(data);
      dispatch(loginSuccess(result));
      navigate('/dashboard');
    } catch (error) {
      console.error('Login failed', error);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-muted">
      <Card className="w-full max-w-sm shadow-lg border-0">
        <CardHeader>
          <CardTitle className="text-center text-2xl font-bold">Sign in to BillBuddy</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
            <div>
              <Label htmlFor="email">Email</Label>
              <Input id="email" type="email" {...register('email')} placeholder="you@email.com" autoFocus />
            </div>
            <div>
              <Label htmlFor="password">Password</Label>
              <Input id="password" type="password" {...register('password')} placeholder="••••••••" />
            </div>
            <Button type="submit" className="w-full">Login</Button>
            <div className="text-center text-sm mt-2">
              Don&apos;t have an account? <a href="/register" className="underline text-primary">Register</a>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}

export default LoginPage;