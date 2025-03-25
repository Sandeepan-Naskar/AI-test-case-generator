import React, { useState } from "react";
import { depositMoney } from "../api/v1/bankingApi";

const DepositMoney = () => {
  const [data, setData] = useState({ accountNumber: "", amount: "" });

  const handleChange = (e) => {
    setData({ ...data, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    try {
      await depositMoney(data);
      alert("Deposit Successful!");
    } catch (error) {
      alert("Deposit Failed.");
    }
  };

  return (
    <div>
      <h2>Deposit Money</h2>
      <input type="text" name="accountNumber" placeholder="Account Number" onChange={handleChange} />
      <input type="number" name="amount" placeholder="Amount" onChange={handleChange} />
      <button onClick={handleSubmit}>Deposit</button>
    </div>
  );
};

export default DepositMoney;
