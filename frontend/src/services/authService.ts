import {authApi} from './api';

export const login = async (credentials: { email: string; password: string }) => {
  const response = await authApi.post('/auth/login', credentials);
  return response.data;
};

export const register = async (data: { email: string; password: string }) => {
  const response = await authApi.post('/auth/signup', data);
  return response.data;
};

export const getCurrentUser = async() => {
  const response = await authApi.post( `/auth/validate/user` );
  return response.data;
}