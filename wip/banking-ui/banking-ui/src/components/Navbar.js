import React from "react";
import { Link } from "react-router-dom";
import "./Navbar.css";

const Navbar = () => {
  return (
    <nav className="navbar">
      <h1>💰 Banking App</h1>
      <div className="nav-links">
        <Link to="/">Home</Link>
        <Link to="/accounts">Accounts</Link>
        <Link to="/transactions">Transactions</Link>
        <Link to="/check-balance">Check Balance</Link>
        <Link to="/create-account">Create Account</Link> {/* ✅ Fixed */}
      </div>
    </nav>
  );
};

export default Navbar;
