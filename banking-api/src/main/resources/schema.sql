CREATE TABLE IF NOT EXISTS account (
    account_number VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    balance DOUBLE NOT NULL,
    currency VARCHAR(3) NOT NULL
);
