const express = require('express');
const router = express.Router();
const orderService = require('../services/orderServiceClient');
const { verifyToken } = require('../middleware/authMiddleware');

router.get('/', verifyToken, async (req, res, next) => {
  try {
    const userId = req.user.userId;
    const data = await orderService.getOrdersByUserId(userId);
    res.status(200).json(data);
  } catch (err) {
    next(err);
  }
});

router.get('/:id', verifyToken, async (req, res, next) => {
  try {
    const data = await orderService.getOrderById(req.params.id);
    res.status(200).json(data);
  } catch (err) {
    next(err);
  }
});

module.exports = router;
