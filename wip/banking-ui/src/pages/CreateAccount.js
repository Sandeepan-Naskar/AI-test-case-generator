import { useState } from "react";
import { createAccount } from "../api/bankingApi";
import Navbar from "../components/Navbar";

export default function CreateAccount() {
  const [name, setName] = useState("");
  const [balance, setBalance] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    await createAccount({ name, balance, currency: "INR" });
    alert("Account Created!");
  };

  return (
    <div className="container">
      <Navbar />
      <h2>Create Account</h2>
      <form onSubmit={handleSubmit}>
        <label>Name:</label>
        <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
        <label>Balance:</label>
        <input type="number" value={balance} onChange={(e) => setBalance(e.target.value)} required />
        <button className="button" type="submit">Create</button>
      </form>
    </div>
  );
}
