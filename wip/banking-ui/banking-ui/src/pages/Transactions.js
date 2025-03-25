import React, { useState } from "react";
import axios from "axios";
import Navbar from "../components/Navbar";

const Transactions = () => {
  const [type, setType] = useState("deposit");
  const [formData, setFormData] = useState({ accountNumber: "", amount: "", fromAccount: "", toAccount: "" });
  const [message, setMessage] = useState(""); // Success or error message
  const [isError, setIsError] = useState(false); // Track if it's an error message

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    let url = "";
    let data = {};

    if (type === "deposit") {
      url = "http://192.168.1.16:8080/api/v1/transfers/deposit";
      data = { accountNumber: formData.accountNumber, amount: formData.amount };
    } else if (type === "withdraw") {
      url = "http://192.168.1.16:8080/api/v1/transfers/withdraw";
      data = { accountNumber: formData.accountNumber, amount: formData.amount };
    } else if (type === "transfer") {
      url = "http://192.168.1.16:8080/api/v1/transfers";
      data = { fromAccount: formData.fromAccount, toAccount: formData.toAccount, amount: formData.amount };
    }

    try {
      const response = await axios.post(url, data);
      if (response.data.status === "success") {
        setMessage(response.data.message);
        setIsError(false);
      } else {
        setMessage(response.data.message);
        setIsError(true);
      }
    } catch (error) {
      if (error.response) {
        setMessage(error.response.data.message || "Transaction failed due to server error.");
      } else {
        setMessage("Network error. Please try again.");
      }
      setIsError(true);
    }
  };

  return (
    <div>
      <Navbar />
      <h2>Transactions</h2>

      <select onChange={(e) => setType(e.target.value)} value={type}>
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

      {message && (
        <p style={{ color: isError ? "red" : "green", marginTop: "10px" }}>
          {message}
        </p>
      )}
    </div>
  );
};

export default Transactions;
