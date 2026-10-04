const express = require('express');
const router = express.Router();
const productService = require('../services/productServiceClient');

router.get('/categories', async (req, res, next) => {
  try {
    const data = await productService.getCategories();
    res.status(200).json(data);
  } catch (err) {
    next(err);
  }
});

router.get('/products', async (req, res, next) => {
  try {
    const data = await productService.getProducts(req.query);
    res.status(200).json(data);
  } catch (err) {
    next(err);
  }
});

router.get('/products/:id', async (req, res, next) => {
  try {
    const data = await productService.getProductById(req.params.id);
    res.status(200).json(data);
  } catch (err) {
    next(err);
  }
});

module.exports = router;
