const axios = require('axios');
const config = require('../config/environment');

const client = axios.create({
  baseURL: config.services.user,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json'
  }
});

const register = async (userData) => {
  const response = await client.post('/users/register', userData);
  return response.data;
};

const login = async (credentials) => {
  const response = await client.post('/users/login', credentials);
  return response.data;
};

const getProfile = async (userId, token) => {
  const headers = {};
  if (token) headers['Authorization'] = `Bearer ${token}`;
  if (userId) headers['X-User-Id'] = userId;

  const response = await client.get('/users/profile', { headers });
  return response.data;
};

module.exports = {
  register,
  login,
  getProfile
};
