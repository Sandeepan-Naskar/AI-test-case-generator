import { useState } from "react";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import axios from "axios";

function Home() {
  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold">Banking App</h1>
      <nav className="mt-4">
        <Link className="mr-4 text-blue-500" to="/create-account">Create Account</Link>
        <Link className="mr-4 text-blue-500" to="/balance-check">Check Balance</Link>
        <Link className="mr-4 text-blue-500" to="/transactions">Transactions</Link>
      </nav>
    </div>
  );
}

function CreateAccount() {
  const [name, setName] = useState("");
  const [balance, setBalance] = useState("");
  const [currency, setCurrency] = useState("INR");

  const handleSubmit = async (e) => {
    e.preventDefault();
    await axios.post("/api/accounts", { name, balance, currency });
    alert("Account Created!");
  };

  return (
    <div className="p-6">
      <h2 className="text-xl font-bold">Create Account</h2>
      <form onSubmit={handleSubmit} className="mt-4">
        <input className="border p-2" type="text" placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} required />
        <input className="border p-2 ml-2" type="number" placeholder="Balance" value={balance} onChange={(e) => setBalance(e.target.value)} required />
        <button className="bg-blue-500 text-white px-4 py-2 ml-2" type="submit">Create</button>
      </form>
    </div>
  );
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/create-account" element={<CreateAccount />} />
      </Routes>
    </Router>
  );
}

export default App;
