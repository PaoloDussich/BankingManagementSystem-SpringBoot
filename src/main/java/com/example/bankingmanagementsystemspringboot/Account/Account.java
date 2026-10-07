package com.example.bankingmanagementsystemspringboot.Account;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.Objects;

@JsonPropertyOrder({"id", "accountNumber", "pinHash", "fullName", "phone", "balance"})
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String accountNumber;

    @JsonIgnore
    private String pinHash;
    private String fullName;
    private String phone;
    private BigDecimal balance;


    public Account(){

    }

    public Integer getId(){
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPinHash() {
        return pinHash;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public boolean credit(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            return false;
        }
        balance = balance.add(amount);
        return true;
    }

    public boolean debit(BigDecimal amount){



        if(amount.compareTo(BigDecimal.ZERO) <= 0 || amount.compareTo(balance) > 0){
            return false;
        }

        balance = balance.subtract(amount);
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(pinHash, account.pinHash) && Objects.equals(fullName, account.fullName) && Objects.equals(phone, account.phone) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, pinHash, fullName, phone, balance);
    }
}




