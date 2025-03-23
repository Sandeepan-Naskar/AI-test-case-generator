package com.bank.controllers;

import com.bank.models.Account;
import com.bank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create a new account
    @PostMapping
    public Map<String, Object> createAccount(@RequestBody Account newAccount) {
        Map<String, Object> response = new HashMap<>();

        if (accountRepository.existsById(newAccount.getAccountNumber())) {
            response.put("status", "failed");
            response.put("message", "Account already exists");
            return response;
        }

        accountRepository.save(newAccount);
        response.put("status", "success");
        response.put("message", "Account created successfully");
        response.put("account", newAccount);
        return response;
    }

    // Get balance for an account
    @GetMapping("/{accountNumber}/balance")
    public Map<String, Object> getBalance(@PathVariable String accountNumber) {
        Map<String, Object> response = new HashMap<>();
        Optional<Account> account = accountRepository.findById(accountNumber);

        if (account.isEmpty()) {
            response.put("status", "failed");
            response.put("message", "Account not found");
            return response;
        }

        response.put("status", "success");
        response.put("accountNumber", accountNumber);
        response.put("balance", account.get().getBalance());
        response.put("currency", account.get().getCurrency());
        return response;
    }

    // Get all accounts
    @GetMapping

    public Map<String, Object> getAllAccounts() {
        Map<String, Object> response = new HashMap<>();
        List<Account> accounts = accountRepository.findAll();
        response.put("status", "success");
        response.put("accounts", accounts);
        return response;
    }


    // Get account by ID
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable String accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // Delete an account by ID
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            accountRepository.delete(account.get());
            return ResponseEntity.ok("Account deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Account not found.");
        }
    }
}
