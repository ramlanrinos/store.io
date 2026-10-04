import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { Trash2, ShoppingBag, ArrowRight } from 'lucide-react';

const CartPage = () => {
  const { cart, updateCartItem, removeCartItem } = useCart();
  const navigate = useNavigate();

  if (!cart || !cart.items || cart.items.length === 0) {
    return (
      <div className="container" style={{ padding: '4rem 0', textAlign: 'center' }}>
        <div style={{ background: '#ffffff', padding: '3rem', borderRadius: '0.75rem', border: '1px solid #e2e8f0', maxWidth: '500px', margin: '0 auto' }}>
          <ShoppingBag size={48} style={{ color: '#94a3b8', marginBottom: '1rem' }} />
          <h2 style={{ fontSize: '1.5rem', fontWeight: 700, color: '#0f172a' }}>Your Cart is Empty</h2>
          <p style={{ color: '#64748b', marginTop: '0.5rem', marginBottom: '1.5rem' }}>Looks like you haven't added any items to your cart yet.</p>
          <Link to="/" className="btn btn-primary">
            Explore Products
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="container" style={{ padding: '2rem 0' }}>
      <h1 style={{ fontSize: '2rem', fontWeight: 800, color: '#0f172a', marginBottom: '1.5rem' }}>Shopping Cart</h1>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 340px', gap: '2rem' }}>
        {/* Cart Items Table */}
        <div className="table-container" style={{ marginTop: 0 }}>
          <table>
            <thead>
              <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Subtotal</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {cart.items.map((item) => (
                <tr key={item.id}>
                  <td>
                    <div style={{ fontWeight: 600, color: '#0f172a' }}>{item.productName}</div>
                    <div style={{ fontSize: '0.75rem', color: '#64748b' }}>SKU: {item.sku}</div>
                  </td>
                  <td>${parseFloat(item.price).toFixed(2)}</td>
                  <td>
                    <input
                      type="number"
                      min="1"
                      className="form-input"
                      style={{ width: '70px', padding: '0.375rem 0.5rem' }}
                      value={item.quantity}
                      onChange={(e) => updateCartItem(item.id, parseInt(e.target.value) || 1)}
                    />
                  </td>
                  <td style={{ fontWeight: 700, color: '#0f172a' }}>
                    ${parseFloat(item.itemSubtotal).toFixed(2)}
                  </td>
                  <td>
                    <button
                      onClick={() => removeCartItem(item.id)}
                      style={{ background: 'none', border: 'none', color: '#ef4444', cursor: 'pointer' }}
                      title="Remove Item"
                    >
                      <Trash2 size={18} />
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Cart Summary Card */}
        <div style={{ background: '#ffffff', padding: '1.5rem', borderRadius: '0.75rem', border: '1px solid #e2e8f0', height: 'fit-content' }}>
          <h3 style={{ fontSize: '1.25rem', fontWeight: 700, color: '#0f172a', marginBottom: '1.25rem' }}>Order Summary</h3>

          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.75rem', color: '#475569' }}>
            <span>Total Items:</span>
            <strong>{cart.totalItemsCount}</strong>
          </div>

          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1.5rem', fontSize: '1.25rem', fontWeight: 800, color: '#0f172a', borderTop: '1px solid #e2e8f0', paddingTop: '1rem' }}>
            <span>Subtotal:</span>
            <span>${parseFloat(cart.subtotal).toFixed(2)}</span>
          </div>

          <button onClick={() => navigate('/checkout')} className="btn btn-primary btn-full">
            <span>Proceed to Checkout</span>
            <ArrowRight size={18} />
          </button>
        </div>
      </div>
    </div>
  );
};

export default CartPage;
