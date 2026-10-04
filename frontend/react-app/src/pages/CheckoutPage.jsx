import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import bffClient from '../api/bffClient';
import { useCart } from '../context/CartContext';
import { CreditCard, CheckCircle2 } from 'lucide-react';

const CheckoutPage = () => {
  const { cart, clearCartState } = useCart();
  const navigate = useNavigate();
  const [shippingAddress, setShippingAddress] = useState('123 Broadway St, New York, NY 10001');
  const [paymentMethod, setPaymentMethod] = useState('CREDIT_CARD');
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');

  if (!cart || !cart.items || cart.items.length === 0) {
    return (
      <div className="container" style={{ padding: '4rem 0', textAlign: 'center' }}>
        <h2>No items in cart to checkout</h2>
        <button onClick={() => navigate('/')} className="btn btn-primary" style={{ marginTop: '1rem' }}>
          Go to Catalog
        </button>
      </div>
    );
  }

  const handleCheckout = async (e) => {
    e.preventDefault();
    setError('');
    setSubmitting(true);

    try {
      const res = await bffClient.post('/checkout', {
        shippingAddress,
        paymentMethod
      });

      clearCartState();
      alert(`Order Placed Successfully!\nOrder Reference: ${res.data.order.orderNumber}`);
      navigate('/orders');
    } catch (err) {
      setError(err.response?.data?.detail || err.message || 'Checkout failed.');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="container" style={{ padding: '2rem 0' }}>
      <h1 style={{ fontSize: '2rem', fontWeight: 800, color: '#0f172a', marginBottom: '1.5rem' }}>Checkout &amp; Payment</h1>

      {error && <div className="alert alert-danger">{error}</div>}

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 340px', gap: '2rem' }}>
        {/* Shipping & Payment Form */}
        <form onSubmit={handleCheckout} style={{ background: '#ffffff', padding: '2rem', borderRadius: '0.75rem', border: '1px solid #e2e8f0' }}>
          <h3 style={{ fontSize: '1.25rem', fontWeight: 700, color: '#0f172a', marginBottom: '1.25rem' }}>1. Shipping Address</h3>
          <div className="form-group">
            <label className="form-label">Delivery Address</label>
            <textarea
              className="form-input"
              rows="3"
              required
              value={shippingAddress}
              onChange={(e) => setShippingAddress(e.target.value)}
            />
          </div>

          <h3 style={{ fontSize: '1.25rem', fontWeight: 700, color: '#0f172a', margin: '2rem 0 1.25rem' }}>2. Payment Options</h3>
          <div className="form-group">
            <label className="form-label">Payment Gateway</label>
            <select
              className="form-input"
              value={paymentMethod}
              onChange={(e) => setPaymentMethod(e.target.value)}
            >
              <option value="CREDIT_CARD">Credit / Debit Card (Simulated)</option>
              <option value="MOCK_GATEWAY">Mock Payment Channel</option>
            </select>
          </div>

          <button type="submit" disabled={submitting} className="btn btn-primary btn-full" style={{ marginTop: '2rem' }}>
            <CreditCard size={18} />
            <span>{submitting ? 'Processing Checkout Saga...' : 'Pay & Confirm Order'}</span>
          </button>
        </form>

        {/* Summary Card */}
        <div style={{ background: '#ffffff', padding: '1.5rem', borderRadius: '0.75rem', border: '1px solid #e2e8f0', height: 'fit-content' }}>
          <h3 style={{ fontSize: '1.25rem', fontWeight: 700, color: '#0f172a', marginBottom: '1.25rem' }}>Checkout Summary</h3>

          {cart.items.map((item) => (
            <div key={item.id} style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.75rem', fontSize: '0.9rem' }}>
              <span>{item.quantity}x {item.productName}</span>
              <strong>${parseFloat(item.itemSubtotal).toFixed(2)}</strong>
            </div>
          ))}

          <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '1rem', paddingTop: '1rem', borderTop: '1px solid #e2e8f0', fontSize: '1.25rem', fontWeight: 800 }}>
            <span>Total:</span>
            <span>${parseFloat(cart.subtotal).toFixed(2)}</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CheckoutPage;
