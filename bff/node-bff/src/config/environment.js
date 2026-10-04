require('dotenv').config();

module.exports = {
  port: process.env.PORT || 5000,
  nodeEnv: process.env.NODE_ENV || 'development',
  jwtSecret: process.env.JWT_SECRET || '9a4f2c8d7e1b5a3f8c6d4e2b0a1f9e8d7c5b3a1f4e2d0c9b8a7f6e5d4c3b2a1f',
  services: {
    user: process.env.USER_SERVICE_URL || 'http://localhost:8081',
    product: process.env.PRODUCT_SERVICE_URL || 'http://localhost:8082',
    inventory: process.env.INVENTORY_SERVICE_URL || 'http://localhost:8083',
    cart: process.env.CART_SERVICE_URL || 'http://localhost:8084',
    order: process.env.ORDER_SERVICE_URL || 'http://localhost:8085',
    payment: process.env.PAYMENT_SERVICE_URL || 'http://localhost:8086'
  }
};
