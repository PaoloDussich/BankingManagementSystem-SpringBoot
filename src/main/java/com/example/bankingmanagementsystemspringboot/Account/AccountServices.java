package com.example.bankingmanagementsystemspringboot.Account;

import com.example.bankingmanagementsystemspringboot.Users.Users;
import com.example.bankingmanagementsystemspringboot.Users.UsersRepository;

import java.util.ArrayList;
import java.util.Random;

public class AccountServices {

    private final AccountRepository accountRepository;
    private final Random random = new Random();

    public AccountServices(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public int randomNumber() {
        return random.nextInt(10000);
    }

    public String generatorAccountNumber() {
        String accountNumber;

        do {
            accountNumber = "ACC" + randomNumber();

        } while (accountRepository.findByAccountNumber(accountNumber) != null);
        return accountNumber;

    }


    public String createAccountServices(String pinHash, String userName, String phoneNumber, double balance) {
        Account account = new Account();

        String accountNumber = generatorAccountNumber();

        Users user = UsersRepository.find(userName);

        if (user == null) {

            return "User does not exist.";
        }


        user.setAccountNumber(accountNumber);
        accountRepository.save(userName, accountNumber, '6');

        pinHash = Utils.hashSha(pinHash);

        account.setAccountNumber(accountNumber);
        account.setPinHash(pinHash);
        account.setFullName(userName);
        account.setPhone(phoneNumber);
        account.setBalance(balance);

        fileRepository.writeAccountFile(account);

        return "Account created successfully.";
    }


    public Account findAccount(String accountNumber) {

        return fileRepository.findAccount(accountNumber);

    }

    public ArrayList<Account> showAccountList() {
        return fileRepository.showAccountList();

    }

    public void deletedAccount(String userName) {
        fileRepository.deleteAccount(userName);

    }


    public void updateAccount(String userAccount, String newPin, char updateOption) {

        if (updateOption == '2') {
            newPin = Utils.hashSha(newPin);
        }

        fileRepository.updateAccount(userAccount, newPin, updateOption);
    }


}
