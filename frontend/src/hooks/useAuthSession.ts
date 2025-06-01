import { useQuery } from '@tanstack/react-query';
import api from '../services/api';

export const useAuthSession = () => {
  return useQuery({
    queryKey: ['auth', 'me'],
    queryFn: async () => {
      const { data } = await api.post('/auth/login'); // Backend must support this route
      return data;
    },
    retry: false,
  });
};
