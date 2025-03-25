package com.bank.controllers;

import com.bank.models.Account;
import com.bank.models.Transaction;
import com.bank.models.TransactionType;
import com.bank.repositories.AccountRepository;
import com.bank.repositories.TransactionRepository;
import com.bank.services.FraudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferController {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final FraudService fraudService;

    @Autowired
    public TransferController(AccountRepository accountRepository,
                              TransactionRepository transactionRepository,
                              FraudService fraudService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.fraudService = fraudService;
    }

    @PostMapping
    public Map<String, Object> transferFunds(@RequestBody Map<String, Object> transferDetails) {
        String fromAccount = (String) transferDetails.get("fromAccount");
        String toAccount = (String) transferDetails.get("toAccount");
        //double amount = (double) transferDetails.get("amount");
        Object amountObj = transferDetails.get("amount");

        double amount;
        if (amountObj instanceof Number) {
            amount = ((Number) amountObj).doubleValue();  // ✅ Handles both Integer & Double
        } else {
            amount = Double.parseDouble(amountObj.toString());  // ✅ Converts String to Double
        }

        Map<String, Object> response = new HashMap<>();

        // Fraud Check
        if (fraudService.isFraudulent(amount)) {
            response.put("status", "failed");
            response.put("message", "Fraud detected! Transfer blocked.");
            return response;
        }

        // Check if both accounts exist
        Optional<Account> senderOpt = accountRepository.findById(fromAccount);
        Optional<Account> receiverOpt = accountRepository.findById(toAccount);

        if (senderOpt.isEmpty() || receiverOpt.isEmpty()) {
            response.put("status", "failed");
            response.put("message", "One or both accounts not found.");
            return response;
        }

        Account sender = senderOpt.get();
        Account receiver = receiverOpt.get();

        // Check sufficient funds
        if (sender.getBalance() < amount) {
            response.put("status", "failed");
            response.put("message", "Insufficient funds.");
            return response;
        }

        // Process the transfer
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        // Save the transaction
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setType(TransactionType.TRANSFER);

        transactionRepository.save(transaction);

        response.put("status", "success");
        response.put("message", "Transfer completed successfully.");
        response.put("fromAccount", fromAccount);
        response.put("toAccount", toAccount);
        response.put("amount", amount);

        return response;
    }

    @GetMapping("/transactions")
    public Map<String, Object> getAllTransactions(@RequestParam(required = false) String accountNumber) {
        Map<String, Object> response = new HashMap<>();

        if (accountNumber != null) {
            Optional<Account> accountOpt = accountRepository.findById(accountNumber);
            if (accountOpt.isEmpty()) {
                response.put("status", "failed");
                response.put("message", "Account not found.");
                return response;
            }

            List<Transaction> transactions = transactionRepository.findByFromAccountOrToAccount(accountNumber, accountNumber);
            response.put("status", "success");
            response.put("transactions", transactions);
        } else {
            List<Transaction> transactions = transactionRepository.findAll();
            response.put("status", "success");
            response.put("transactions", transactions);
        }

        return response;
    }

    @PostMapping("/deposit")
    public Map<String, Object> depositFunds(@RequestBody Map<String, Object> depositDetails) {
        String accountNumber = (String) depositDetails.get("accountNumber");
        //double amount = (double) depositDetails.get("amount");
        Object amountObj = depositDetails.get("amount");

        double amount;
        if (amountObj instanceof Number) {
            amount = ((Number) amountObj).doubleValue();  // ✅ Handles both Integer & Double
        } else {
            amount = Double.parseDouble(amountObj.toString());  // ✅ Converts String to Double
        }


        Map<String, Object> response = new HashMap<>();
        Optional<Account> accountOpt = accountRepository.findById(accountNumber);

        if (accountOpt.isEmpty()) {
            response.put("status", "failed");
            response.put("message", "Account not found.");
            return response;
        }

        Account account = accountOpt.get();
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(null);
        transaction.setToAccount(accountNumber);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setType(TransactionType.DEPOSIT);

        transactionRepository.save(transaction);

        response.put("status", "success");
        response.put("message", "Deposit successful.");
        response.put("balance", account.getBalance());

        return response;
    }

    @PostMapping("/withdraw")
    public Map<String, Object> withdrawFunds(@RequestBody Map<String, Object> withdrawDetails) {
        String accountNumber = (String) withdrawDetails.get("accountNumber");
        Object amountObj = withdrawDetails.get("amount");

        double amount;
        if (amountObj instanceof Number) {
            amount = ((Number) amountObj).doubleValue();  // ✅ Handles both Integer & Double
        } else {
            amount = Double.parseDouble(amountObj.toString());  // ✅ Converts String to Double
        }
        //double amount = (double) withdrawDetails.get("amount");

        Map<String, Object> response = new HashMap<>();
        Optional<Account> accountOpt = accountRepository.findById(accountNumber);

        if (accountOpt.isEmpty()) {
            response.put("status", "failed");
            response.put("message", "Account not found.");
            return response;
        }

        Account account = accountOpt.get();

        if (account.getBalance() < amount) {
            response.put("status", "failed");
            response.put("message", "Insufficient funds.");
            return response;
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(accountNumber);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setType(TransactionType.WITHDRAW);

        transactionRepository.save(transaction);

        response.put("status", "success");
        response.put("message", "Withdrawal successful.");
        response.put("accountNumber", accountNumber);
        response.put("newBalance", account.getBalance());

        return response;
    }
}
