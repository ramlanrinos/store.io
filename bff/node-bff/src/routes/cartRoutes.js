const express = require('express');
const router = express.Router();
const cartService = require('../services/cartServiceClient');
const productService = require('../services/productServiceClient');
const { verifyToken } = require('../middleware/authMiddleware');

router.get('/', verifyToken, async (req, res, next) => {
  try {
    const userId = req.user.userId;
    const rawCart = await cartService.getCart(userId);

    if (!rawCart.items || rawCart.items.length === 0) {
      return res.status(200).json({
        id: rawCart.id,
        userId: userId,
        items: [],
        totalItemsCount: 0,
        subtotal: 0.0,
        updatedAt: rawCart.updatedAt
      });
    }

    const productIds = rawCart.items.map(i => i.productId);
    const productDetails = await productService.getProductsBatch(productIds);
    const productMap = new Map(productDetails.map(p => [p.id, p]));

    let calculatedSubtotal = 0.0;
    const aggregatedItems = rawCart.items.map(item => {
      const product = productMap.get(item.productId) || {};
      const unitPrice = product.price || 0.0;
      const itemSubtotal = parseFloat((unitPrice * item.quantity).toFixed(2));
      calculatedSubtotal += itemSubtotal;

      return {
        id: item.id,
        productId: item.productId,
        productName: product.name || 'Unknown Product',
        sku: product.sku || 'N/A',
        price: unitPrice,
        quantity: item.quantity,
        itemSubtotal: itemSubtotal,
        addedAt: item.addedAt
      };
    });

    res.status(200).json({
      id: rawCart.id,
      userId: userId,
      items: aggregatedItems,
      totalItemsCount: rawCart.totalItemsCount,
      subtotal: parseFloat(calculatedSubtotal.toFixed(2)),
      updatedAt: rawCart.updatedAt
    });
  } catch (err) {
    next(err);
  }
});

router.post('/items', verifyToken, async (req, res, next) => {
  try {
    const userId = req.user.userId;
    await cartService.addItem(userId, req.body);
    res.redirect(303, '/api/cart');
  } catch (err) {
    next(err);
  }
});

router.put('/items/:cartItemId', verifyToken, async (req, res, next) => {
  try {
    const userId = req.user.userId;
    await cartService.updateItem(userId, req.params.cartItemId, req.body);
    res.redirect(303, '/api/cart');
  } catch (err) {
    next(err);
  }
});

router.delete('/items/:cartItemId', verifyToken, async (req, res, next) => {
  try {
    const userId = req.user.userId;
    await cartService.removeItem(userId, req.params.cartItemId);
    res.redirect(303, '/api/cart');
  } catch (err) {
    next(err);
  }
});

router.delete('/', verifyToken, async (req, res, next) => {
  try {
    const userId = req.user.userId;
    await cartService.clearCart(userId);
    res.status(204).send();
  } catch (err) {
    next(err);
  }
});

module.exports = router;
