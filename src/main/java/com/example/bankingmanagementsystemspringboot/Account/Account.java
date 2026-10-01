package com.example.bankingmanagementsystemspringboot.Account;


import java.util.Objects;

public class Account {
    private String AccountNumber;
    private String PinHash;
    private String FullName;
    private String Phone;
    private double Balance;


    public Account(){

    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getPinHash() {
        return PinHash;
    }

    public void setPinHash(String pinHash) {
        PinHash = pinHash;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public boolean credit(double amount){
        if (amount <=0){
            return false;
        }
        Balance += amount;
        return true;
    }

    public boolean debit(double amount){
        if(amount <= 0 || amount > Balance){
            return false;
        }

        Balance -= amount;
        return true;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(Balance, account.Balance) == 0 && Objects.equals(AccountNumber, account.AccountNumber) && Objects.equals(PinHash, account.PinHash) && Objects.equals(FullName, account.FullName) && Objects.equals(Phone, account.Phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(AccountNumber, PinHash, FullName, Phone, Balance);
    }
}




