import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import bffClient from '../api/bffClient';
import { useCart } from '../context/CartContext';
import { useAuth } from '../context/AuthContext';
import { ShoppingCart, Package, ArrowLeft } from 'lucide-react';

const ProductDetailPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState(null);
  const [quantity, setQuantity] = useState(1);
  const [loading, setLoading] = useState(true);
  const { addToCart } = useCart();
  const { user } = useAuth();

  useEffect(() => {
    bffClient.get(`/products/${id}`)
      .then(res => setProduct(res.data))
      .catch(err => console.error('Failed to load product:', err))
      .finally(() => setLoading(false));
  }, [id]);

  const handleAddToCart = async () => {
    if (!user) {
      alert('Please log in to add items to your shopping cart.');
      return;
    }
    try {
      await addToCart(product.id, quantity);
      alert(`Added ${quantity}x "${product.name}" to cart!`);
    } catch (err) {
      alert('Failed to add to cart: ' + (err.response?.data?.detail || err.message));
    }
  };

  if (loading) {
    return <div className="container" style={{ padding: '4rem 0', textAlign: 'center' }}>Loading product details...</div>;
  }

  if (!product) {
    return (
      <div className="container" style={{ padding: '4rem 0', textAlign: 'center' }}>
        <h2>Product Not Found</h2>
        <button onClick={() => navigate('/')} className="btn btn-secondary" style={{ marginTop: '1rem' }}>
          Back to Catalog
        </button>
      </div>
    );
  }

  return (
    <div className="container" style={{ padding: '2rem 0' }}>
      <button onClick={() => navigate('/')} className="btn btn-secondary" style={{ marginBottom: '1.5rem' }}>
        <ArrowLeft size={16} />
        <span>Back to Catalog</span>
      </button>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(320px, 1fr))', gap: '3rem', background: '#ffffff', padding: '2.5rem', borderRadius: '0.75rem', border: '1px solid #e2e8f0' }}>
        <div style={{ background: '#f8fafc', borderRadius: '0.5rem', height: '320px', display: 'flex', alignItems: 'center', justifyContent: 'center', color: '#94a3b8' }}>
          <Package size={96} />
        </div>

        <div style={{ display: 'flex', flexDirection: 'column' }}>
          <span style={{ fontSize: '0.85rem', fontWeight: 700, color: '#2563eb', textTransform: 'uppercase', marginBottom: '0.5rem' }}>
            {product.categoryName}
          </span>
          <h1 style={{ fontSize: '2rem', fontWeight: 800, color: '#0f172a', marginBottom: '0.5rem' }}>{product.name}</h1>
          <p style={{ fontSize: '0.875rem', color: '#64748b', marginBottom: '1.5rem' }}>SKU: <strong>{product.sku}</strong></p>

          <div style={{ fontSize: '2rem', fontWeight: 800, color: '#0f172a', marginBottom: '1.5rem' }}>
            ${parseFloat(product.price).toFixed(2)}
          </div>

          <p style={{ color: '#475569', lineHeight: 1.6, marginBottom: '2rem' }}>
            {product.description || 'High quality product available for immediate dispatch.'}
          </p>

          <div style={{ display: 'flex', gap: '1rem', alignItems: 'center', marginTop: 'auto' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
              <label className="form-label" style={{ marginBottom: 0 }}>Qty:</label>
              <input
                type="number"
                min="1"
                max="99"
                className="form-input"
                style={{ width: '80px' }}
                value={quantity}
                onChange={(e) => setQuantity(parseInt(e.target.value) || 1)}
              />
            </div>

            <button onClick={handleAddToCart} className="btn btn-primary" style={{ flexGrow: 1 }}>
              <ShoppingCart size={18} />
              <span>Add to Cart</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetailPage;
