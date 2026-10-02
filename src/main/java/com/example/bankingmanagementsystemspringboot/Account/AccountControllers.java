package com.example.bankingmanagementsystemspringboot.Account;


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
    public String createAccount(@PathVariable Integer id, @RequestBody Account account){
      return   accountServices.createAccountServices(account, id);
    }

    @GetMapping
    public ResponseEntity<ArrayList<Account>> showAccountList() {
        return ResponseEntity.ok(accountServices.showAccountList());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> showListForAccountNumber(@PathVariable String accountNumber) {
        Account account = accountServices.findAccount(accountNumber);

        if (account == null) {
            return ResponseEntity.notFound().build();
        }



        return ResponseEntity.ok(account);
    }


    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Integer id){
        return accountServices.deletedAccount(id);
    }

    @PutMapping("/{id}")
    public String updateAccount(@PathVariable Integer id, @RequestBody Account account){
        return  accountServices.updateAccount(id, account);
    }


}
