const axios = require('axios');
const config = require('../config/environment');

const client = axios.create({
  baseURL: config.services.cart,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json'
  }
});

const getCart = async (userId) => {
  const response = await client.get(`/carts/${userId}`);
  return response.data;
};

const addItem = async (userId, itemData) => {
  const response = await client.post(`/carts/${userId}/items`, itemData);
  return response.data;
};

const updateItem = async (userId, cartItemId, itemData) => {
  const response = await client.put(`/carts/${userId}/items/${cartItemId}`, itemData);
  return response.data;
};

const removeItem = async (userId, cartItemId) => {
  const response = await client.delete(`/carts/${userId}/items/${cartItemId}`);
  return response.data;
};

const clearCart = async (userId) => {
  const response = await client.delete(`/carts/${userId}`);
  return response.data;
};

module.exports = {
  getCart,
  addItem,
  updateItem,
  removeItem,
  clearCart
};
