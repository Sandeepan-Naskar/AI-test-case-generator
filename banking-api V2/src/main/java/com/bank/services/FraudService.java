package com.bank.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FraudService {

    private static final Logger logger = LoggerFactory.getLogger(FraudService.class);
    private static final double FRAUD_THRESHOLD = 10000.0;

    public boolean isFraudulent(double amount) {
        boolean isFraud = amount > FRAUD_THRESHOLD;
        if (isFraud) {
            logger.warn("🚨 Fraud detected! Amount: " + amount);
        }
        return isFraud;
    }
}
