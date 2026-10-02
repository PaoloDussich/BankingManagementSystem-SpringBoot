package com.example.bankingmanagementsystemspringboot.Transactions;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private final TransactionServices transactionServices;

    public TransactionController(TransactionServices transactionServices){
        this.transactionServices=transactionServices;
    }


    @PostMapping("/deposit")
    public ResponseEntity<String> createTransaction(@RequestBody TransactionRecord transactionRecord) {

        String result = transactionServices.createDeposit(transactionRecord);

        return ResponseEntity.status(201).body(result);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<String> createWithdrawal(@RequestBody TransactionRecord transactionRecord) {
        String result = transactionServices.createWithdrawal(transactionRecord);
        return ResponseEntity.status(201).body(result);
    }



    @GetMapping
    public ResponseEntity<ArrayList<TransactionRecord>> showHistory() {

        return ResponseEntity.ok(transactionServices.showHistory());
    }




    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<ArrayList<TransactionRecord>> showHistoryAboutOneAccount(@PathVariable String accountNumber) {

        return ResponseEntity.ok(transactionServices.showHistoryAboutOneAccount(accountNumber));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionRecord> findTransaction(@PathVariable String transactionId) {

        TransactionRecord transaction = transactionServices.findTransaction(transactionId);

        if (transaction == null) {
            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(transaction);
    }




}
