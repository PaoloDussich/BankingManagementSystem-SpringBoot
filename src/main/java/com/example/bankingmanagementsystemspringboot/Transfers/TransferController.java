package com.example.bankingmanagementsystemspringboot.Transfers;

import com.example.bankingmanagementsystemspringboot.Transactions.DTO.TransactionDTO;
import com.example.bankingmanagementsystemspringboot.Transfers.TransferDTO.TransferDTO;
import com.example.bankingmanagementsystemspringboot.Users.Permissions;
import com.example.bankingmanagementsystemspringboot.Users.Users;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> createTransfer(@Valid @RequestBody TransferDTO transferDTO, HttpSession session) {

        Users users = (Users) session.getAttribute("User");

        if (users != null){
            if (users.hasPermission(Permissions.TRANSFER)&& users.getAccountNumber().equals(transferDTO.getSourceAccount())){

                return ResponseEntity.status(201).body(transferServices.createTransfer(transferDTO));

            }
            return ResponseEntity.status(403).body("User haven't permission for do it this transaction");

        }
        return ResponseEntity.status(401).body("User isn't logged in");

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
