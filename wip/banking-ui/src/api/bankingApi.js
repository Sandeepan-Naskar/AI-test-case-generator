import axios from "axios";

const API_BASE_URL = "http://192.168.1.16:8080/api/v1";

export const createAccount = (data) => axios.post(`${API_BASE_URL}/accounts`, data);
export const checkBalance = (accountNumber) => axios.get(`${API_BASE_URL}/accounts/${accountNumber}/balance`);
export const depositMoney = (data) => axios.post(`${API_BASE_URL}/transfers/deposit`, data);
export const withdrawMoney = (data) => axios.post(`${API_BASE_URL}/transfers/withdraw`, data);
export const transferMoney = (data) => axios.post(`${API_BASE_URL}/transfers`, data);
export const getTransactions = (accountNumber) => axios.get(`${API_BASE_URL}/transfers/transactions`, { params: { accountNumber } });
