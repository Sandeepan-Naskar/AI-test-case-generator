import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import Accounts from "./pages/Accounts";
import Transactions from "./pages/Transactions";
import CheckBalance from "./pages/CheckBalance"; // Import new page
import CreateAccount from "./pages/CreateAccount";

const App = () => {
  return (
    <Router>
      <Navbar />
      <div className="container">
        
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/create-account" element={<CreateAccount />} />
          <Route path="/accounts" element={<Accounts />} />
          <Route path="/transactions" element={<Transactions />} />
          <Route path="/check-balance" element={<CheckBalance />} /> {/* ✅ Add Route */}
        </Routes>
      </div>
    </Router>
  );
};

export default App;



