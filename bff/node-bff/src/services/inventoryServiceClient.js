const axios = require('axios');
const config = require('../config/environment');

const client = axios.create({
  baseURL: config.services.inventory,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json'
  }
});

const getInventory = async (productId) => {
  const response = await client.get(`/inventory/${productId}`);
  return response.data;
};

const reserveStock = async (orderNumber, items) => {
  const response = await client.post('/inventory/reserve', { orderNumber, items });
  return response.data;
};

const releaseStock = async (orderNumber, items) => {
  const response = await client.post('/inventory/release', { orderNumber, items });
  return response.data;
};

const deductStock = async (orderNumber, items) => {
  const response = await client.post('/inventory/deduct', { orderNumber, items });
  return response.data;
};

module.exports = {
  getInventory,
  reserveStock,
  releaseStock,
  deductStock
};
