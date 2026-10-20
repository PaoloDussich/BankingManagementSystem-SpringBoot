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
    public ResponseEntity<String> createTransfer(@Valid @RequestBody TransferDTO transferDTO, @RequestHeader("Idempotency-Key") String idempotencyKey, HttpSession session) {
        Users users = (Users) session.getAttribute("User");

        if (users == null){
            return  ResponseEntity.status(401).body("User isn't logged in");

        }

        if (!users.hasPermission(Permissions.TRANSFER)){
            return ResponseEntity.status(403).body("User doesn't have permission to make transfers");
        }

        if (users.getAccountNumber() == null) {
            return ResponseEntity.badRequest().body("The authenticated user doesn't have an account");
        }

        transferDTO.setSourceAccount(users.getAccountNumber());
        String result = transferServices.createTransfer(transferDTO,idempotencyKey);

        return ResponseEntity.status(result.startsWith("Transfer already processed")?200:201).body(result);

    }


    @GetMapping
    public ResponseEntity<?> showHistory(HttpSession session) {
        Users user = (Users) session.getAttribute("User");

        if (user == null) {

            return ResponseEntity.status(401).body("User isn't logged in");
        }

        if (!user.hasPermission(Permissions.VIEW_TRANSFERS)) {

            return ResponseEntity.status(403).body("Permission denied");


        }

        return ResponseEntity.ok(transferServices.showHistoryAboutOneAccount(user.getAccountNumber()));
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<?> showHistoryAboutOneAccount(
            @PathVariable String accountNumber, HttpSession session) {

        Users user = (Users) session.getAttribute("User");

        if (user == null) {
            return ResponseEntity.status(401).body("User isn't logged in");
        }

        if (!user.hasPermission(Permissions.VIEW_TRANSFERS)) {

            return ResponseEntity.status(403).body("Permission denied");
        }

        if (!user.getAccountNumber().equals(accountNumber)) {
            return ResponseEntity.status(403).body("You cannot view another user's transfers");
        }

        return ResponseEntity.ok(transferServices.showHistoryAboutOneAccount(accountNumber));
    }



    @GetMapping("/{transferId}")
    public ResponseEntity<?> findTransaction(
            @PathVariable String transferId, HttpSession session) {

        Users user = (Users) session.getAttribute("User");

        if (user == null) {
            return ResponseEntity.status(401).body("User isn't logged in");

        }



        if (!user.hasPermission(Permissions.VIEW_TRANSFERS)) {
            return ResponseEntity.status(403).body("Permission denied");
        }

        TransferRecord transfer = transferServices.findTransaction(transferId);

        if (transfer == null) {
            return ResponseEntity.notFound().build();

        }

        boolean ownsSource = user.getAccountNumber() != null && user.getAccountNumber().equals(transfer.getSourceAccount());

        boolean ownsTarget = user.getAccountNumber() != null && user.getAccountNumber().equals(transfer.getTargetAccount());

        if (!ownsSource && !ownsTarget) {
            return ResponseEntity.status(403).build();

        }

        return ResponseEntity.ok(transfer);
    }

}
