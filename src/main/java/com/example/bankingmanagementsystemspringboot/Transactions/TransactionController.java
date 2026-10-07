package com.example.bankingmanagementsystemspringboot.Transactions;



import com.example.bankingmanagementsystemspringboot.Transactions.DTO.TransactionDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private final TransactionServices transactionServices;

    public TransactionController(TransactionServices transactionServices){
        this.transactionServices=transactionServices;
    }


    @PostMapping("/deposit")
    public ResponseEntity<String> createTransaction(@Valid  @RequestBody TransactionDTO transactionDTO) {

        return ResponseEntity.status(201).body(transactionServices.createDeposit(transactionDTO));

    }

    @PostMapping("/withdrawal")
    public ResponseEntity<String> createWithdrawal(@Valid @RequestBody TransactionDTO transactionDTO) {

        return ResponseEntity.status(201).body(transactionServices.createWithdrawal(transactionDTO));
    }



    @GetMapping
    public ResponseEntity<ArrayList<TransactionRecord>> showHistory() {

        return ResponseEntity.ok(transactionServices.showHistory());
    }




    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionRecord>> showHistoryAboutOneAccount(@PathVariable String accountNumber) {

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
