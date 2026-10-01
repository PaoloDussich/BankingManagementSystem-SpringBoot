package com.example.bankingmanagementsystemspringboot.Account;


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
    public ArrayList<Account> mostrarListaAccount(){
        return accountServices.showAccountList();
    }

    @GetMapping("/{accountNumber}")
    public Account showListForAccountNumber(@PathVariable String accountNumber){
        return accountServices.findAccount(accountNumber);
    }

    @DeleteMapping("/{accountNumber}")
    public void deleteAccount(@PathVariable String accountNumber){
        accountServices.deletedAccount(accountNumber);
    }

    @PutMapping("/{accountNumber}")
    public void updateAccount(@PathVariable String accountNumber, @RequestBody Account account){
        accountServices.updateAccount(accountNumber, account);
    }


}
