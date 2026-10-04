const axios = require('axios');
const config = require('../config/environment');

const client = axios.create({
  baseURL: config.services.order,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json'
  }
});

const createOrder = async (orderData) => {
  const response = await client.post('/orders', orderData);
  return response.data;
};

const getOrderById = async (id) => {
  const response = await client.get(`/orders/${id}`);
  return response.data;
};

const getOrdersByUserId = async (userId) => {
  const response = await client.get(`/orders/user/${userId}`);
  return response.data;
};

const updateOrderStatus = async (id, statusData) => {
  const response = await client.put(`/orders/${id}/status`, statusData);
  return response.data;
};

module.exports = {
  createOrder,
  getOrderById,
  getOrdersByUserId,
  updateOrderStatus
};
