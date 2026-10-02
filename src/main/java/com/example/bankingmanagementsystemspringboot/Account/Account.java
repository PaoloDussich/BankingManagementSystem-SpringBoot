package com.example.bankingmanagementsystemspringboot.Account;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@JsonPropertyOrder({"id", "accountNumber", "pinHash", "fullName", "phone", "balance"})
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String accountNumber;
    private String pinHash;
    private String fullName;
    private String phone;
    private double balance;


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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean credit(double amount){
        if (amount <=0){
            return false;
        }
        balance += amount;
        return true;
    }

    public boolean debit(double amount){
        if(amount <= 0 || amount > balance){
            return false;
        }

        balance -= amount;
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(balance, account.balance) == 0 && Objects.equals(id, account.id) && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(pinHash, account.pinHash) && Objects.equals(fullName, account.fullName) && Objects.equals(phone, account.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, pinHash, fullName, phone, balance);
    }
}




