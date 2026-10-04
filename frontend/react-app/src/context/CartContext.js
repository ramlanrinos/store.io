import React, { createContext, useState, useEffect, useContext } from 'react';
import bffClient from '../api/bffClient';
import { useAuth } from './AuthContext';

const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const { user } = useAuth();
  const [cart, setCart] = useState(null);
  const [cartItemCount, setCartItemCount] = useState(0);

  const fetchCart = async () => {
    if (!user) {
      setCart(null);
      setCartItemCount(0);
      return;
    }
    try {
      const res = await bffClient.get('/cart');
      setCart(res.data);
      setCartItemCount(res.data.totalItemsCount || 0);
    } catch (err) {
      console.error('Failed to fetch cart:', err);
    }
  };

  useEffect(() => {
    fetchCart();
  }, [user]);

  const addToCart = async (productId, quantity = 1) => {
    await bffClient.post('/cart/items', { productId, quantity });
    await fetchCart();
  };

  const updateCartItem = async (cartItemId, quantity) => {
    await bffClient.put(`/cart/items/${cartItemId}`, { quantity });
    await fetchCart();
  };

  const removeCartItem = async (cartItemId) => {
    await bffClient.delete(`/cart/items/${cartItemId}`);
    await fetchCart();
  };

  const clearCartState = () => {
    setCart(null);
    setCartItemCount(0);
  };

  return (
    <CartContext.Provider value={{ cart, cartItemCount, fetchCart, addToCart, updateCartItem, removeCartItem, clearCartState }}>
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => useContext(CartContext);
