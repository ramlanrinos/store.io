const axios = require('axios');
const config = require('../config/environment');

const client = axios.create({
  baseURL: config.services.product,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json'
  }
});

const getCategories = async () => {
  const response = await client.get('/categories');
  return response.data;
};

const getProducts = async (queryParams) => {
  const response = await client.get('/products', { params: queryParams });
  return response.data;
};

const getProductById = async (id) => {
  const response = await client.get(`/products/${id}`);
  return response.data;
};

const getProductsBatch = async (productIds) => {
  const response = await client.post('/products/batch', { productIds });
  return response.data;
};

module.exports = {
  getCategories,
  getProducts,
  getProductById,
  getProductsBatch
};
