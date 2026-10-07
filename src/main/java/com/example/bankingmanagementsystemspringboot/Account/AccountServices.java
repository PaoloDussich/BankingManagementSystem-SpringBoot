package com.example.bankingmanagementsystemspringboot.Account;

import com.example.bankingmanagementsystemspringboot.Account.DTO.CreateAccountDTO;
import com.example.bankingmanagementsystemspringboot.Account.DTO.UpdateAccountDTO;
import com.example.bankingmanagementsystemspringboot.Exception.ResourceNotFoundException;
import com.example.bankingmanagementsystemspringboot.Users.Users;
import com.example.bankingmanagementsystemspringboot.Users.UsersRepository;

import com.example.bankingmanagementsystemspringboot.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public String createAccountServices(CreateAccountDTO account, Integer id) {

        //este es el usurio con ese id
        Users users = usersRepository.findById(id).orElse(null);

        if (users == null) {
            throw new ResourceNotFoundException("User doesn't exist");
        }

        if (users.getAccountNumber() != null) {
            throw new IllegalArgumentException("User already has an account");
        }

        Account newAccount = new Account();

        String accountNumber = "ACC" + users.getId();

        users.setAccountNumber(accountNumber);
        usersRepository.save(users);


        newAccount.setAccountNumber(accountNumber);
        newAccount.setFullName(users.getUserName());
        newAccount.setPinHash(Utils.hashPassword(account.getPin()));
        newAccount.setPhone(account.getPhone());
        newAccount.setBalance(BigDecimal.ZERO);


        accountRepository.save(newAccount);
        return "Account created successfully";
    }

@Transactional
    public Account findAccount(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (account == null) {
            throw new ResourceNotFoundException("Account doesn't exist");
        }
        return account;
    }

    public ArrayList<Account> showAccountList() {
        return new ArrayList<>(accountRepository.findAll());

    }


    @Transactional
    public String deletedAccount(Integer id) {
        Account account = accountRepository.findById(id).orElse(null);


        if (account == null) {
            throw new ResourceNotFoundException("Account doesn't exist");
        }

        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("Account has funds it can't be deleted");


        }

        Users user = usersRepository.findByAccountNumber(account.getAccountNumber());

        if (user == null) {
            throw new ResourceNotFoundException("User doesn't exist");
        }

        if (user.getAccountNumber() == null) {
            throw new IllegalArgumentException("User doesn't have an associated account number");

        }

        user.setAccountNumber(null);
        usersRepository.save(user);


        accountRepository.delete(account);
        return "Account deleted successfully ";
    }


    public String updateAccount(Integer id, UpdateAccountDTO accounts) {

        Account account = accountRepository.findById(id).orElse(null);

        if (account == null) {

            throw new ResourceNotFoundException("Account doesn't exist");
        }


        if (accounts.getPhone() != null) {
            account.setPhone(accounts.getPhone());

        }

        if (accounts.getPin() != null) {
            account.setPinHash(Utils.hashPassword(accounts.getPin()));

        }

        accountRepository.save(account);
        return "Account updated successfully ";

    }


}
