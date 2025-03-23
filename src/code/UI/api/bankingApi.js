import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api";

export const createAccount = (data) => axios.post(`${API_BASE_URL}/accounts`, data);
export const checkBalance = (accountNumber) => axios.get(`${API_BASE_URL}/accounts/${accountNumber}/balance`);
export const depositMoney = (data) => axios.post(`${API_BASE_URL}/transactions/deposit`, data);
export const withdrawMoney = (data) => axios.post(`${API_BASE_URL}/transactions/withdraw`, data);
export const transferMoney = (data) => axios.post(`${API_BASE_URL}/transactions/transfer`, data);
export const getTransactions = () => axios.get(`${API_BASE_URL}/transactions`);
