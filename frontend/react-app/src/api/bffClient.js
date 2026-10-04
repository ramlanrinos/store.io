import axios from 'axios';

const bffClient = axios.create({
  baseURL: process.env.REACT_APP_BFF_URL || 'http://localhost:5000/api',
  headers: {
    'Content-Type': 'application/json'
  }
});

bffClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('jwt_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

export default bffClient;
