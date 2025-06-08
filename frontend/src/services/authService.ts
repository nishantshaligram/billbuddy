import api from './api';

export const login = async (credentials: { email: string; password: string }) => {
  const response = await api.post('/auth/login', credentials);
  return response.data;
};

export const register = async (data: { email: string; password: string }) => {
  const response = await api.post('/auth/signup', data);
  return response.data;
};

export const getCurrentUser = async( userId: number ) => {
  const response = await api.get( `/users/${userId}` );
  return response.data;
}