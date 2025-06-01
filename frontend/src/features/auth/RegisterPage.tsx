import { useForm } from 'react-hook-form';
import { register as registerUser } from '../../services/authService';
import { useNavigate } from 'react-router-dom';

function RegisterPage() {
  const { register, handleSubmit } = useForm();
  const navigate = useNavigate();

  const onSubmit = async (data: any) => {
    try {
      const result = await registerUser(data);
      result.length
      navigate('/login');
    } catch (error) {
      console.error('Registration failed', error);
    }
  };

  return (
    <div className="max-w-sm mx-auto mt-10">
      <h2 className="text-xl font-bold mb-4">Register</h2>
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <input {...register('email')} placeholder="Email" className="w-full p-2 border rounded" />
        <input {...register('password')} type="password" placeholder="Password" className="w-full p-2 border rounded" />
        <button type="submit" className="w-full bg-black text-white p-2 rounded">Register</button>
      </form>
    </div>
  );
}

export default RegisterPage;