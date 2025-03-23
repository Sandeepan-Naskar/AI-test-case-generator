import { useState } from "react";
import { checkBalance } from "../api/bankingApi";
import Navbar from "../components/Navbar";

export default function CheckBalance() {
  const [accountNumber, setAccountNumber] = useState("");
  const [balance, setBalance] = useState(null);

  const handleCheck = async () => {
    const result = await checkBalance(accountNumber);
    setBalance(result.balance);
  };

  return (
    <div className="container">
      <Navbar />
      <h2>Check Balance</h2>
      <label>Account Number:</label>
      <input type="text" value={accountNumber} onChange={(e) => setAccountNumber(e.target.value)} required />
      <button className="button" onClick={handleCheck}>Check</button>
      {balance !== null && <p>Balance: ₹{balance}</p>}
    </div>
  );
}
