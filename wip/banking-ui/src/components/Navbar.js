import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <nav style={{ background: "#333", padding: "10px", color: "white" }}>
      <Link style={{ marginRight: "15px", color: "white" }} to="/">Home</Link>
      <Link style={{ marginRight: "15px", color: "white" }} to="/create-account">Create Account</Link>
      <Link style={{ marginRight: "15px", color: "white" }} to="/check-balance">Check Balance</Link>
      <Link style={{ marginRight: "15px", color: "white" }} to="/transactions">Transactions</Link>
    </nav>
  );
}
