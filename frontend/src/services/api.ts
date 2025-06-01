import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // Spring Boot URL
  withCredentials: true,            // for cookies/JWT tokens if needed
});

export default api;