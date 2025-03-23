import { useState, useEffect } from "react";
import { checkBalance } from "../api/bankingApi";
import Navbar from "../components/Navbar";
import InputField from "../components/InputField";

export default function CheckBalance() {
  const [accountNumber, setAccountNumber] = useState("");
  const [balance, setBalance] = useState(null);

  const handleCheck = async () => {
    const result = await checkBalance(accountNumber);
    setBalance(result.balance);
  };

  return (
    <div className="p-6">
      <Navbar />
      <h2 className="text-xl font-bold">Check Balance</h2>
      <InputField label="Account Number" type="text" value={accountNumber} onChange={(e) => setAccountNumber(e.target.value)} />
      <button className="bg-blue-500 text-white px-4 py-2" onClick={handleCheck}>Check</button>
      {balance !== null && <p className="mt-4">Balance: ₹{balance}</p>}
    </div>
  );
}
