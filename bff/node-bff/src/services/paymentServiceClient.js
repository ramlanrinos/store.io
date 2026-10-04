const axios = require('axios');
const config = require('../config/environment');

const client = axios.create({
  baseURL: config.services.payment,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json'
  }
});

const processPayment = async (paymentData) => {
  const response = await client.post('/payments/process', paymentData);
  return response.data;
};

const getPaymentById = async (id) => {
  const response = await client.get(`/payments/${id}`);
  return response.data;
};

const getPaymentByOrderId = async (orderId) => {
  const response = await client.get(`/payments/order/${orderId}`);
  return response.data;
};

const refundPayment = async (id, refundData) => {
  const response = await client.post(`/payments/${id}/refund`, refundData);
  return response.data;
};

module.exports = {
  processPayment,
  getPaymentById,
  getPaymentByOrderId,
  refundPayment
};
