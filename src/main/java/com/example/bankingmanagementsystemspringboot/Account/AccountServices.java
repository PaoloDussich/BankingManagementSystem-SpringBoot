package com.example.bankingmanagementsystemspringboot.Account;

import com.example.bankingmanagementsystemspringboot.Users.Users;
import com.example.bankingmanagementsystemspringboot.Users.UsersRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;



@Service
public class AccountServices {

    private final AccountRepository accountRepository;
    private final UsersRepository usersRepository;


    public AccountServices(AccountRepository accountRepository, UsersRepository usersRepository) {
        this.accountRepository = accountRepository;
        this.usersRepository = usersRepository;
    }

@Transactional
    public String createAccountServices(Account account, Integer id ) {

        //este es el usurio con ese id
        Users users = usersRepository.findById(id).orElse(null);

        if (users ==null){
            return "User doesn't exist";
        }

        if (users.getAccountNumber() != null) {
            return "User already has an account";
        }

        String accountNumber = "ACC"+users.getId();

        users.setAccountNumber(accountNumber);
        usersRepository.save(users);

        account.setFullName(users.getUserName());

        account.setAccountNumber(accountNumber);

        accountRepository.save(account);
        return "Account created successfully";
    }


    public Account findAccount(String accountNumber) {

        return accountRepository.findByAccountNumber(accountNumber);

    }

    public ArrayList<Account> showAccountList() {
        return new ArrayList<>(accountRepository.findAll());

    }

    public String deletedAccount(Integer id ) {
        Account account = accountRepository.findById(id).orElse(null);


        if (account == null) {
            return "Account doesn't exist";
        }

        accountRepository.delete(account);
        return "Account deleted successfully ";
    }


    public String updateAccount(Integer id, Account accounts) {

        Account account = accountRepository.findById(id).orElse(null);

        if (account == null){
            return "Account doesn't exist";
        }


        account.setPhone(accounts.getPhone());
        account.setPinHash(accounts.getPinHash());

        accountRepository.save(account);
        return "Account updated successfully ";

    }


}
