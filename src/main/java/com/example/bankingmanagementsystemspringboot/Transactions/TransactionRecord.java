package com.example.bankingmanagementsystemspringboot.Transactions;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@JsonPropertyOrder({"id", "transactionId", "accountNumber", "amount", "resultingBalance", "executedBy", "type", "tImeStamp"})
@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String transactionId;
    private String accountNumber;
    private double amount;
    private double resultingBalance;
    private String executedBy;
    private String type;
    private  String tImeStamp;

    public  TransactionRecord(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getResultingBalance() {
        return resultingBalance;
    }

    public void setResultingBalance(double resultingBalance) {
        this.resultingBalance = resultingBalance;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String gettImeStamp() {
        return tImeStamp;
    }

    public void settImeStamp(String tImeStamp) {
        this.tImeStamp = tImeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionRecord that = (TransactionRecord) o;
        return Double.compare(amount, that.amount) == 0 && Double.compare(resultingBalance, that.resultingBalance) == 0 && Objects.equals(id, that.id) && Objects.equals(transactionId, that.transactionId) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(executedBy, that.executedBy) && Objects.equals(type, that.type) && Objects.equals(tImeStamp, that.tImeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionId, accountNumber, amount, resultingBalance, executedBy, type, tImeStamp);
    }
}
