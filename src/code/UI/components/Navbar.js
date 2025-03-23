import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <nav className="p-4 bg-gray-800 text-white">
      <Link className="mr-4" to="/">Home</Link>
      <Link className="mr-4" to="/create-account">Create Account</Link>
      <Link className="mr-4" to="/check-balance">Check Balance</Link>
      <Link className="mr-4" to="/transactions">Transactions</Link>
    </nav>
  );
}
