package com.bank.controllers;

import com.bank.models.Account;
import com.bank.models.Transaction;
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
        double amount = (double) transferDetails.get("amount");

        Map<String, Object> response = new HashMap<>();

        // Fraud Check using FraudService
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

        transactionRepository.save(transaction);

        response.put("status", "success");
        response.put("message", "Transfer completed successfully.");
        response.put("fromAccount", fromAccount);
        response.put("toAccount", toAccount);
        response.put("amount", amount);

        return response;
    }

    // Get all transactions (history)
    @GetMapping("/transactions")
    public Map<String, Object> getAllTransactions() {
        Map<String, Object> response = new HashMap<>();
        List<Transaction> transactions = transactionRepository.findAll();
        response.put("status", "success");
        response.put("transactions", transactions);
        return response;
    }
}
