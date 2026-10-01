package com.example.bankingmanagementsystemspringboot.Account;

import com.example.bankingmanagementsystemspringboot.Users.Users;
import com.example.bankingmanagementsystemspringboot.Users.UsersRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;



@Service
public class AccountServices {

    private final AccountRepository accountRepository;
    private final UsersRepository usersRepository;


    public AccountServices(AccountRepository accountRepository, UsersRepository usersRepository) {
        this.accountRepository = accountRepository;
        this.usersRepository = usersRepository;
    }


    public String createAccountServices(Account account, Integer id ) {

        //este es el usurio con ese id
        Users users = usersRepository.findById(id).orElse(null);

        if (users ==null){
            return "User doesn't exits";
        }

        String accountNumber = "ACC"+users.getId();

        users.setAccountNumber(accountNumber);
        usersRepository.save(users);

        account.setFullName(users.getUserName());

        account.setAccountNumber(accountNumber);

        accountRepository.save(account);
        return "Account created successfully.";
    }


    public Account findAccount(String accountNumber) {

        return accountRepository.findByAccountNumber(accountNumber);

    }

    public ArrayList<Account> showAccountList() {
        return new ArrayList<>(accountRepository.findAll());

    }

    public void deletedAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        accountRepository.delete(account);
    }


    public void updateAccount(String accountNumber, Account accounts) {

        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (account == null){
            return;
        }

        
        account.setPhone(accounts.getPhone());
        account.setPinHash(accounts.getPinHash());

        accountRepository.save(account);
    }


}
