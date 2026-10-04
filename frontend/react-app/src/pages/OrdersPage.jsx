import React, { useState, useEffect } from 'react';
import bffClient from '../api/bffClient';
import { Package, Calendar, MapPin } from 'lucide-react';

const OrdersPage = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    bffClient.get('/orders')
      .then(res => setOrders(res.data || []))
      .catch(err => console.error('Failed to load orders:', err))
      .finally(() => setLoading(false));
  }, []);

  const getStatusBadge = (status) => {
    switch (status) {
      case 'PAID': return <span className="badge badge-paid">PAID</span>;
      case 'PENDING': return <span className="badge badge-pending">PENDING</span>;
      case 'PAYMENT_FAILED': return <span className="badge badge-failed">PAYMENT FAILED</span>;
      case 'SHIPPED': return <span className="badge badge-shipped">SHIPPED</span>;
      default: return <span className="badge">{status}</span>;
    }
  };

  return (
    <div className="container" style={{ padding: '2rem 0' }}>
      <h1 style={{ fontSize: '2rem', fontWeight: 800, color: '#0f172a', marginBottom: '1.5rem' }}>Order History</h1>

      {loading ? (
        <div style={{ textAlign: 'center', padding: '4rem 0', color: '#64748b' }}>Loading order history...</div>
      ) : orders.length === 0 ? (
        <div style={{ background: '#ffffff', padding: '3rem', borderRadius: '0.75rem', border: '1px solid #e2e8f0', textAlign: 'center' }}>
          <Package size={48} style={{ color: '#94a3b8', marginBottom: '1rem' }} />
          <h3>No Orders Placed Yet</h3>
          <p style={{ color: '#64748b', marginTop: '0.5rem' }}>Your past orders will appear here after checkout.</p>
        </div>
      ) : (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
          {orders.map((order) => (
            <div key={order.id} style={{ background: '#ffffff', borderRadius: '0.75rem', border: '1px solid #e2e8f0', overflow: 'hidden' }}>
              <div style={{ padding: '1.25rem 1.5rem', background: '#f8fafc', borderBottom: '1px solid #e2e8f0', display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem' }}>
                <div>
                  <div style={{ fontSize: '1.1rem', fontWeight: 700, color: '#0f172a' }}>{order.orderNumber}</div>
                  <div style={{ fontSize: '0.85rem', color: '#64748b', display: 'flex', alignItems: 'center', gap: '0.4rem', marginTop: '0.2rem' }}>
                    <Calendar size={14} />
                    <span>Placed on {new Date(order.createdAt).toLocaleDateString()}</span>
                  </div>
                </div>

                <div style={{ display: 'flex', alignItems: 'center', gap: '1.5rem' }}>
                  {getStatusBadge(order.status)}
                  <div style={{ fontSize: '1.25rem', fontWeight: 800, color: '#0f172a' }}>
                    ${parseFloat(order.totalAmount).toFixed(2)}
                  </div>
                </div>
              </div>

              <div style={{ padding: '1.5rem' }}>
                <div style={{ marginBottom: '1rem', fontSize: '0.875rem', color: '#475569', display: 'flex', alignItems: 'center', gap: '0.4rem' }}>
                  <MapPin size={16} />
                  <span>Shipping Address: {order.shippingAddress}</span>
                </div>

                <div style={{ borderTop: '1px solid #f1f5f9', paddingTop: '1rem' }}>
                  <h4 style={{ fontSize: '0.9rem', fontWeight: 700, color: '#334155', marginBottom: '0.75rem' }}>Items:</h4>
                  {order.items.map((item) => (
                    <div key={item.id} style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.5rem', fontSize: '0.95rem' }}>
                      <span>{item.quantity}x {item.productName} (Price snapshot: ${parseFloat(item.priceSnapshot).toFixed(2)})</span>
                      <strong style={{ color: '#0f172a' }}>${parseFloat(item.itemSubtotal).toFixed(2)}</strong>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default OrdersPage;
