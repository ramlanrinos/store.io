const express = require('express');
const router = express.Router();
const cartService = require('../services/cartServiceClient');
const productService = require('../services/productServiceClient');
const inventoryService = require('../services/inventoryServiceClient');
const orderService = require('../services/orderServiceClient');
const paymentService = require('../services/paymentServiceClient');
const { verifyToken } = require('../middleware/authMiddleware');

router.post('/', verifyToken, async (req, res, next) => {
  const userId = req.user.userId;
  const { shippingAddress, paymentMethod } = req.body;

  if (!shippingAddress) {
    return res.status(400).json({
      type: 'https://store.io/errors/bad-request',
      title: 'Bad Request',
      status: 400,
      detail: 'Shipping address is required for checkout',
      instance: req.originalUrl,
      timestamp: new Date().toISOString()
    });
  }

  let createdOrder = null;
  let reservedStockItems = [];

  try {
    const rawCart = await cartService.getCart(userId);
    if (!rawCart.items || rawCart.items.length === 0) {
      return res.status(400).json({
        type: 'https://store.io/errors/empty-cart',
        title: 'Empty Cart',
        status: 400,
        detail: 'Cannot checkout an empty shopping cart',
        instance: req.originalUrl,
        timestamp: new Date().toISOString()
      });
    }

    const productIds = rawCart.items.map(i => i.productId);
    const productDetails = await productService.getProductsBatch(productIds);
    const productMap = new Map(productDetails.map(p => [p.id, p]));

    const orderItems = rawCart.items.map(item => {
      const product = productMap.get(item.productId);
      if (!product) {
        throw new Error(`Product ID ${item.productId} no longer exists in catalog`);
      }
      return {
        productId: item.productId,
        productName: product.name,
        priceSnapshot: product.price,
        quantity: item.quantity
      };
    });

    createdOrder = await orderService.createOrder({
      userId,
      shippingAddress,
      items: orderItems
    });

    reservedStockItems = rawCart.items.map(i => ({
      productId: i.productId,
      quantity: i.quantity
    }));

    try {
      await inventoryService.reserveStock(createdOrder.orderNumber, reservedStockItems);
    } catch (stockErr) {
      await orderService.updateOrderStatus(createdOrder.id, { status: 'CANCELLED' });
      throw stockErr;
    }

    try {
      const paymentResponse = await paymentService.processPayment({
        orderId: createdOrder.id,
        amount: createdOrder.totalAmount,
        paymentMethod: paymentMethod || 'CREDIT_CARD'
      });

      await inventoryService.deductStock(createdOrder.orderNumber, reservedStockItems);
      await cartService.clearCart(userId);
      const paidOrder = await orderService.updateOrderStatus(createdOrder.id, { status: 'PAID' });

      return res.status(201).json({
        message: 'Checkout completed successfully',
        order: paidOrder,
        payment: paymentResponse
      });

    } catch (paymentErr) {
      console.warn(`[Checkout Saga] Payment failed for Order ${createdOrder.orderNumber}. Rolling back stock...`);
      await inventoryService.releaseStock(createdOrder.orderNumber, reservedStockItems);
      const failedOrder = await orderService.updateOrderStatus(createdOrder.id, { status: 'PAYMENT_FAILED' });

      return res.status(402).json({
        type: 'https://store.io/errors/payment-failed',
        title: 'Payment Failed',
        status: 402,
        detail: 'Payment processing failed: ' + paymentErr.message,
        instance: req.originalUrl,
        timestamp: new Date().toISOString(),
        order: failedOrder
      });
    }

  } catch (err) {
    next(err);
  }
});

module.exports = router;
