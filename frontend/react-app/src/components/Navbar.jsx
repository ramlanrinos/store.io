import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { ShoppingBag, ShoppingCart, User, LogOut, Package } from 'lucide-react';
import { useAuth } from '../context/AuthContext';
import { useCart } from '../context/CartContext';

const Navbar = () => {
  const { user, logout } = useAuth();
  const { cartItemCount } = useCart();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="container nav-content">
        <Link to="/" className="logo">
          <ShoppingBag className="w-7 h-7 text-blue-600" />
          <span>store.io</span>
        </Link>

        <div className="nav-links">
          <Link to="/" className="nav-link">Products</Link>
          
          <Link to="/cart" className="nav-link">
            <ShoppingCart size={18} />
            <span>Cart</span>
            {cartItemCount > 0 && <span className="cart-badge">{cartItemCount}</span>}
          </Link>

          {user ? (
            <>
              <Link to="/orders" className="nav-link">
                <Package size={18} />
                <span>Orders</span>
              </Link>
              <span className="nav-link" style={{ fontWeight: 600, color: '#0f172a' }}>
                <User size={18} />
                <span>{user.firstName || user.email}</span>
              </span>
              <button onClick={handleLogout} className="btn btn-secondary" style={{ padding: '0.4rem 0.8rem', fontSize: '0.85rem' }}>
                <LogOut size={16} />
                <span>Logout</span>
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="nav-link">Login</Link>
              <Link to="/register" className="btn btn-primary" style={{ padding: '0.4rem 0.8rem', fontSize: '0.85rem' }}>
                Get Started
              </Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
