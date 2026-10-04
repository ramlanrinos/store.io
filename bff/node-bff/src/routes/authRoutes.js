const express = require('express');
const router = express.Router();
const userService = require('../services/userServiceClient');
const { verifyToken } = require('../middleware/authMiddleware');

router.post('/register', async (req, res, next) => {
  try {
    const data = await userService.register(req.body);
    res.status(201).json(data);
  } catch (err) {
    next(err);
  }
});

router.post('/login', async (req, res, next) => {
  try {
    const data = await userService.login(req.body);
    res.status(200).json(data);
  } catch (err) {
    next(err);
  }
});

router.get('/profile', verifyToken, async (req, res, next) => {
  try {
    const token = req.headers.authorization ? req.headers.authorization.split(' ')[1] : null;
    const data = await userService.getProfile(req.user.userId, token);
    res.status(200).json(data);
  } catch (err) {
    next(err);
  }
});

module.exports = router;
