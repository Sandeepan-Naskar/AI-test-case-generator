import React, { useState } from "react";

const CheckBalance = () => {
  const [accountNumber, setAccountNumber] = useState("");
  const [balance, setBalance] = useState(null);
  const [error, setError] = useState("");

  const handleCheckBalance = async () => {
    setError("");
    setBalance(null);

    if (!accountNumber.trim()) {
      setError("Please enter a valid account number.");
      return;
    }

    try {
      const response = await fetch(`http://192.168.1.16:8080/api/accounts/${accountNumber}/balance`);
      if (!response.ok) throw new Error("Account not found!");

      const data = await response.json();
      setBalance(data.balance);
    } catch (err) {
      setError(err.message || "Error fetching balance.");
    }
  };

  return (
    <div className="container">
      <h2>Check Account Balance</h2>
      <input
        type="text"
        placeholder="Enter Account Number"
        value={accountNumber}
        onChange={(e) => setAccountNumber(e.target.value)}
      />
      <button onClick={handleCheckBalance}>Check Balance</button>

      {balance !== null && <p className="success">Balance: ${balance}</p>}
      {error && <p className="error">{error}</p>}
    </div>
  );
};

export default CheckBalance;
