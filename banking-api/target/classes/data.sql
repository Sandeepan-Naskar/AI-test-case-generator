MERGE INTO account USING (VALUES
    ('123456789', 'Alice', 5000.00, 'USD')
) AS v(account_number, name, balance, currency)
ON account.account_number = v.account_number
WHEN MATCHED THEN
    UPDATE SET name = v.name, balance = v.balance, currency = v.currency
WHEN NOT MATCHED THEN
    INSERT (account_number, name, balance, currency)
    VALUES (v.account_number, v.name, v.balance, v.currency);
