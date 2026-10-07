package com.example.bankingmanagementsystemspringboot.Account;


import com.example.bankingmanagementsystemspringboot.Account.DTO.CreateAccountDTO;
import com.example.bankingmanagementsystemspringboot.Account.DTO.UpdateAccountDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/account")
public class AccountControllers {


    private final AccountServices accountServices;


    public AccountControllers(AccountServices accountServices1){
        this.accountServices = accountServices1;

    }

    @PostMapping("/{id}")
    public String createAccount(@PathVariable Integer id, @Valid @RequestBody CreateAccountDTO account){
      return   accountServices.createAccountServices(account, id);
    }




    @GetMapping
    public ResponseEntity<ArrayList<Account>> showAccountList() {
        return ResponseEntity.ok(accountServices.showAccountList());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> showListForAccountNumber(@PathVariable String accountNumber) {

        return ResponseEntity.ok(accountServices.findAccount(accountNumber));
    }


    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Integer id){
        return accountServices.deletedAccount(id);
    }

    @PutMapping("/{id}")
    public String updateAccount(@PathVariable Integer id, @RequestBody UpdateAccountDTO account){
        return accountServices.updateAccount(id, account);
    }


}
