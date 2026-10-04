import React from 'react';
import { Link } from 'react-router-dom';
import { ShoppingCart, Package } from 'lucide-react';
import { useCart } from '../context/CartContext';
import { useAuth } from '../context/AuthContext';

const ProductCard = ({ product }) => {
  const { addToCart } = useCart();
  const { user } = useAuth();

  const handleAddToCart = async (e) => {
    e.preventDefault();
    if (!user) {
      alert('Please log in to add items to your shopping cart.');
      return;
    }
    try {
      await addToCart(product.id, 1);
      alert(`Added "${product.name}" to cart!`);
    } catch (err) {
      alert('Failed to add item to cart: ' + (err.response?.data?.detail || err.message));
    }
  };

  return (
    <div className="product-card">
      <Link to={`/products/${product.id}`}>
        <div className="product-image-placeholder">
          <Package size={48} />
        </div>
      </Link>
      <div className="product-info">
        <span className="product-category">{product.categoryName || 'GENERAL'}</span>
        <Link to={`/products/${product.id}`}>
          <h3 className="product-title">{product.name}</h3>
        </Link>
        <div className="product-price">${parseFloat(product.price).toFixed(2)}</div>
        <button onClick={handleAddToCart} className="btn btn-primary btn-full" style={{ marginTop: 'auto' }}>
          <ShoppingCart size={16} />
          <span>Add to Cart</span>
        </button>
      </div>
    </div>
  );
};

export default ProductCard;
