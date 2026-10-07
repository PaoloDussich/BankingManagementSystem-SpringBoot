package com.example.bankingmanagementsystemspringboot.Transfers;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.Objects;


@JsonPropertyOrder({"id", "transferId", "sourceAccount", "targetAccount", "amount", "timeStamp", "authorizedBy"})
@Entity
public class TransferRecord {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String transferId;
    private String sourceAccount;
    private String targetAccount;
    private BigDecimal amount;
    private String timeStamp;
    private String authorizedBy;

    public TransferRecord(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(String authorizedBy) {
        this.authorizedBy = authorizedBy;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransferRecord that = (TransferRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(transferId, that.transferId) && Objects.equals(sourceAccount, that.sourceAccount) && Objects.equals(targetAccount, that.targetAccount) && Objects.equals(amount, that.amount) && Objects.equals(timeStamp, that.timeStamp) && Objects.equals(authorizedBy, that.authorizedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transferId, sourceAccount, targetAccount, amount, timeStamp, authorizedBy);
    }
}
