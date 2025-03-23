import { useState } from "react";
import { createAccount } from "../api/bankingApi";
import Navbar from "../components/Navbar";
import InputField from "../components/InputField";

export default function CreateAccount() {
  const [name, setName] = useState("");
  const [balance, setBalance] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    await createAccount({ name, balance, currency: "INR" });
    alert("Account Created!");
  };

  return (
    <div className="p-6">
      <Navbar />
      <h2 className="text-xl font-bold">Create Account</h2>
      <form onSubmit={handleSubmit} className="mt-4">
        <InputField label="Name" type="text" value={name} onChange={(e) => setName(e.target.value)} />
        <InputField label="Balance" type="number" value={balance} onChange={(e) => setBalance(e.target.value)} />
        <button className="bg-blue-500 text-white px-4 py-2" type="submit">Create</button>
      </form>
    </div>
  );
}
