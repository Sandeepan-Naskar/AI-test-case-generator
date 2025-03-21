package com.bank.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {

    @Id
    private String accountNumber;

    private String name;
    private double balance;
    private String currency;
}
