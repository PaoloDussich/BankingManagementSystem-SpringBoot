package com.example.bankingmanagementsystemspringboot.Transactions;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@JsonPropertyOrder({"id", "transactionId", "accountNumber", "amount", "resultingBalance", "executedBy", "type", "tImeStamp"})
@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(unique = true)
    private String transactionId;
    private String accountNumber;
    private BigDecimal amount;
    private BigDecimal resultingBalance;
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


    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setResultingBalance(BigDecimal resultingBalance) {
        this.resultingBalance = resultingBalance;
    }


    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }


    public void setType(String type) {
        this.type = type;
    }

    public void settImeStamp(String tImeStamp) {
        this.tImeStamp = tImeStamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getResultingBalance() {
        return resultingBalance;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public String getType() {
        return type;
    }

    public String gettImeStamp() {
        return tImeStamp;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionRecord that = (TransactionRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(transactionId, that.transactionId) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(amount, that.amount) && Objects.equals(resultingBalance, that.resultingBalance) && Objects.equals(executedBy, that.executedBy) && Objects.equals(type, that.type) && Objects.equals(tImeStamp, that.tImeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionId, accountNumber, amount, resultingBalance, executedBy, type, tImeStamp);
    }
}
