package com.bank.services;

import org.springframework.stereotype.Service;

@Service
public class FraudService {

    private static final double FRAUD_THRESHOLD = 10000.0; // ✅ Fraud detection limit

    public boolean isFraudulent(double amount) {
        return amount > FRAUD_THRESHOLD;
    }
}
