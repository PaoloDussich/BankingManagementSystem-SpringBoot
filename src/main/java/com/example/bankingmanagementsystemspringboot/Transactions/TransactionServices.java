package com.example.bankingmanagementsystemspringboot.Transactions;

import com.example.bankingmanagementsystemspringboot.Account.Account;
import com.example.bankingmanagementsystemspringboot.Account.AccountRepository;

import com.example.bankingmanagementsystemspringboot.Users.Users;
import com.example.bankingmanagementsystemspringboot.Users.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import java.util.ArrayList;


@Service
public class TransactionServices {


    private final UsersRepository usersRepository;
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;


    public TransactionServices(TransactionRepository transactionRepository, AccountRepository accountRepository, UsersRepository usersRepository) {
        this.transactionRepository = transactionRepository;

        this.accountRepository = accountRepository;
        this.usersRepository = usersRepository;
    }

    public boolean validateAccount(String accountNumber) {

        if (accountRepository.findByAccountNumber(accountNumber) != null) {
            return true;
        }
        return false;
    }

    private final Random random = new Random();

    public String generateTransactionId() {

        String transactionId;

        do {
            transactionId = "TX" + random.nextInt(100000);
        } while (transactionRepository.findByTransactionId(transactionId) != null);

        return transactionId;
    }


    @Transactional
    public String createDeposit(TransactionRecord transactionRecord) {

        String accountNumber = transactionRecord.getAccountNumber();
        double amount = transactionRecord.getAmount();


        if (!validateAccount(accountNumber)) {
            return "Account does not exist";
        }

        if (amount <= 0) {
            return "The amount must be greater than 0";
        }

        Account account = accountRepository.findByAccountNumber(accountNumber);
        Users user = usersRepository.findByAccountNumber(accountNumber);

        if (user == null) {
            return "User does not exist";
        }

        if (account.credit(amount)) {

            double newBalance = account.getBalance();


            accountRepository.save(account);


            String timeStamp = java.time.LocalDateTime.now().toString();

            String transactionID = generateTransactionId();

            transactionRecord.setTransactionId(transactionID);
            transactionRecord.setAccountNumber(accountNumber);
            transactionRecord.setAmount(amount);
            transactionRecord.setResultingBalance(newBalance);
            transactionRecord.setType("DEPOSIT");
            transactionRecord.setExecutedBy(user.getUserName());
            transactionRecord.settImeStamp(timeStamp);

            transactionRepository.save(transactionRecord);

            return "Deposit completed successfully";
        }
        return "Deposit could not be completed";
    }


    @Transactional
    public String createWithdrawal(TransactionRecord transactionRecord) {


        String accountNumber = transactionRecord.getAccountNumber();
        double amount = transactionRecord.getAmount();


        if (!validateAccount(accountNumber)) {
            return "Account does not exist";

        }
        if (amount <= 0) {
            return "The amount must be greater than 0";

        }

        Account account = accountRepository.findByAccountNumber(accountNumber);
        Users user = usersRepository.findByAccountNumber(accountNumber);

        if (user == null) {
            return "User does not exist";
        }

        if (!account.debit(amount)) {
            return "Insufficient funds";

        }

        double newBalance = account.getBalance();
        accountRepository.save(account);

        String transactionID = generateTransactionId();
        String timeStamp = java.time.LocalDateTime.now().toString();

        transactionRecord.setTransactionId(transactionID);
        transactionRecord.setAccountNumber(accountNumber);
        transactionRecord.setAmount(amount);
        transactionRecord.setResultingBalance(newBalance);

        transactionRecord.setType("WITHDRAW");
        transactionRecord.setExecutedBy(user.getUserName());
        transactionRecord.settImeStamp(timeStamp);

        transactionRepository.save(transactionRecord);
        return "Withdrawal completed successfully";


    }

    public ArrayList<TransactionRecord> showHistory() {

        return new ArrayList<>(transactionRepository.findAll());
    }

    public TransactionRecord findTransaction(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId);
    }

    public ArrayList<TransactionRecord> showHistoryAboutOneAccount(String accountNumber) {
        return new ArrayList<>(transactionRepository.findByAccountNumber(accountNumber));
    }


}
