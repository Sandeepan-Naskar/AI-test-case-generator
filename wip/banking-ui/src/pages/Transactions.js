import React, { useState } from "react";
import axios from "axios";
import Navbar from "../components/Navbar";

const Transactions = () => {
  const [type, setType] = useState("deposit");
  const [formData, setFormData] = useState({ accountNumber: "", amount: "", fromAccount: "", toAccount: "" });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    let url = "";
    let data = {};

    if (type === "deposit") {
      url = "http://192.168.1.16:8080/api/deposit";
      data = { accountNumber: formData.accountNumber, amount: formData.amount };
    } else if (type === "withdraw") {
      url = "http://192.168.1.16:8080/api/withdraw";
      data = { accountNumber: formData.accountNumber, amount: formData.amount };
    } else if (type === "transfer") {
      url = "http://192.168.1.16:8080/api/transfer";
      data = { fromAccount: formData.fromAccount, toAccount: formData.toAccount, amount: formData.amount };
    }

    try {
      await axios.post(url, data);
      alert("Transaction successful!");
    } catch (error) {
      alert("Transaction failed!");
    }
  };

  return (
    <div>
      <Navbar />
      <h2>Transactions</h2>
      <select onChange={(e) => setType(e.target.value)}>
        <option value="deposit">Deposit</option>
        <option value="withdraw">Withdraw</option>
        <option value="transfer">Transfer</option>
      </select>

      <form onSubmit={handleSubmit}>
        {type === "transfer" ? (
          <>
            <input type="text" name="fromAccount" placeholder="From Account" onChange={handleChange} required />
            <input type="text" name="toAccount" placeholder="To Account" onChange={handleChange} required />
          </>
        ) : (
          <input type="text" name="accountNumber" placeholder="Account Number" onChange={handleChange} required />
        )}
        <input type="number" name="amount" placeholder="Amount" onChange={handleChange} required />
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default Transactions;
