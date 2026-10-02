package com.example.bankingmanagementsystemspringboot.Transactions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionRecord, Integer> {
    TransactionRecord findByTransactionId(String transactionId);
   List<TransactionRecord> findByAccountNumber(String accountNumber);

}
