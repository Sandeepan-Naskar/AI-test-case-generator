import React, { useState } from "react";

const CreateAccount = () => {
  const [formData, setFormData] = useState({
    accountNumber: "",
    name: "",
    balance: "",
    currency: "USD",
  });

  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");

    try {
      const response = await fetch("http://192.168.1.16:8080/api/v1/accounts", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      if (!response.ok) throw new Error("Failed to create account!");

      setMessage("✅ Account created successfully!");
    } catch (err) {
      setMessage("❌ Error: " + err.message);
    }
  };

  return (
    <div className="container">
      <h2>Create New Account</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" name="accountNumber" placeholder="Account Number" onChange={handleChange} required />
        <input type="text" name="name" placeholder="Name" onChange={handleChange} required />
        <input type="number" name="balance" placeholder="Initial Balance" onChange={handleChange} required />
        <select name="currency" onChange={handleChange}>
          <option value="USD">USD</option>
          <option value="INR">INR</option>
        </select>
        <button type="submit">Create Account</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default CreateAccount;
