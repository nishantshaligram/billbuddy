import axios from 'axios';

export const authApi = axios.create({
  baseURL: 'http://localhost:8080', // Spring Boot URL
  withCredentials: true,            // for cookies/JWT tokens if needed
});

export const expenseApi = axios.create({
  baseURL: 'http://localhost:8081/api/v1/expense/', // Spring Boot URL
  withCredentials: true,            // for cookies/JWT tokens if needed
});