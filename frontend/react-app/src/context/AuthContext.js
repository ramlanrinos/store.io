import React, { createContext, useState, useEffect, useContext } from 'react';
import bffClient from '../api/bffClient';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem('jwt_token') || null);
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (token) {
      bffClient.get('/auth/profile')
        .then(res => setUser(res.data))
        .catch(() => logout())
        .finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, [token]);

  const login = async (credentials) => {
    const res = await bffClient.post('/auth/login', credentials);
    const { token: jwtToken, userId, email, firstName, lastName, roles } = res.data;
    localStorage.setItem('jwt_token', jwtToken);
    setToken(jwtToken);
    setUser({ id: userId, email, firstName, lastName, roles });
    return res.data;
  };

  const register = async (userData) => {
    const res = await bffClient.post('/auth/register', userData);
    return res.data;
  };

  const logout = () => {
    localStorage.removeItem('jwt_token');
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ token, user, loading, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
