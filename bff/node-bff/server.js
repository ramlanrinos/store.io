const express = require('express');
const cors = require('cors');
const config = require('./src/config/environment');
const errorHandler = require('./src/middleware/errorHandler');

const authRoutes = require('./src/routes/authRoutes');
const productRoutes = require('./src/routes/productRoutes');
const cartRoutes = require('./src/routes/cartRoutes');
const checkoutRoutes = require('./src/routes/checkoutRoutes');
const orderRoutes = require('./src/routes/orderRoutes');

const app = express();

app.use(cors());
app.use(express.json());

app.get('/health', (req, res) => {
  res.status(200).json({
    status: 'UP',
    service: 'node-bff',
    timestamp: new Date().toISOString()
  });
});

app.use('/api/auth', authRoutes);
app.use('/api', productRoutes);
app.use('/api/cart', cartRoutes);
app.use('/api/checkout', checkoutRoutes);
app.use('/api/orders', orderRoutes);

app.use(errorHandler);

app.listen(config.port, () => {
  console.log(`=================================================`);
  console.log(`🚀 store.io Node.js BFF Gateway running on port ${config.port}`);
  console.log(`   Environment: ${config.nodeEnv}`);
  console.log(`=================================================`);
});
