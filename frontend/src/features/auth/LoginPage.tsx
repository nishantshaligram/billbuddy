import { useForm } from 'react-hook-form';
import { useDispatch } from 'react-redux';
import { loginSuccess } from './authSlice';
import { login } from '../../services/authService';
import { useNavigate } from 'react-router-dom';

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
    <div className="max-w-sm mx-auto mt-10">
      <h2 className="text-xl font-bold mb-4">Login</h2>
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <input {...register('email')} placeholder="Email" className="w-full p-2 border rounded" />
        <input {...register('password')} type="password" placeholder="Password" className="w-full p-2 border rounded" />
        <button type="submit" className="w-full bg-black text-white p-2 rounded">Login</button>
      </form>
    </div>
  );
}

export default LoginPage;