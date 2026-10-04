const jwt = require('jsonwebtoken');
const config = require('../config/environment');

const verifyToken = (req, res, next) => {
  const authHeader = req.headers.authorization;

  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({
      type: 'https://store.io/errors/unauthorized',
      title: 'Unauthorized',
      status: 401,
      detail: 'Missing or invalid Authorization token header',
      instance: req.originalUrl,
      timestamp: new Date().toISOString()
    });
  }

  const token = authHeader.split(' ')[1];

  try {
    const decoded = jwt.verify(token, Buffer.from(config.jwtSecret, 'base64'));
    req.user = {
      userId: decoded.userId,
      email: decoded.email,
      roles: decoded.roles || []
    };
    next();
  } catch (err) {
    return res.status(401).json({
      type: 'https://store.io/errors/invalid-token',
      title: 'Unauthorized',
      status: 401,
      detail: 'JWT token verification failed: ' + err.message,
      instance: req.originalUrl,
      timestamp: new Date().toISOString()
    });
  }
};

const optionalToken = (req, res, next) => {
  const authHeader = req.headers.authorization;
  if (authHeader && authHeader.startsWith('Bearer ')) {
    const token = authHeader.split(' ')[1];
    try {
      const decoded = jwt.verify(token, Buffer.from(config.jwtSecret, 'base64'));
      req.user = {
        userId: decoded.userId,
        email: decoded.email,
        roles: decoded.roles || []
      };
    } catch (e) {
      // Ignore invalid token for optional auth
    }
  }
  next();
};

module.exports = {
  verifyToken,
  optionalToken
};
