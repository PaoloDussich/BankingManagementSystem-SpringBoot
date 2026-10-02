package com.example.bankingmanagementsystemspringboot.Transfers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/transfer")
public class TransferController {

    private final TransferServices transferServices;


    public TransferController(TransferServices transferServices) {
        this.transferServices = transferServices;
    }

    @PostMapping
    public ResponseEntity<String> createTransfer(@RequestBody TransferRecord transferRecord) {
        String result = transferServices.createTransfer(transferRecord);

        return ResponseEntity.status(201).body(result);

    }

    @GetMapping
    public ResponseEntity<ArrayList<TransferRecord>> showHistory() {

        return ResponseEntity.ok(transferServices.showHistory());


    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<ArrayList<TransferRecord>> showHistoryAboutOneAccount(@PathVariable String accountNumber) {

        return ResponseEntity.ok(transferServices.showHistoryAboutOneAccount(accountNumber));
    }

    @GetMapping("/{transferId}")
    public ResponseEntity<TransferRecord> findTransaction(@PathVariable String transferId) {

        TransferRecord transfer = transferServices.findTransaction(transferId);

        if (transfer == null) {

            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(transfer);
    }


}
