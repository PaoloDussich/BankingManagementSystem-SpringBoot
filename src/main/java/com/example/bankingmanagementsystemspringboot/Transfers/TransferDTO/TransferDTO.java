package com.example.bankingmanagementsystemspringboot.Transfers.TransferDTO;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class TransferDTO {

    @NotBlank
    private String sourceAccount;
    @NotBlank
    private  String targetAccount;
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
}
