package com.example.bankingmanagementsystemspringboot.Transactions;

import com.example.bankingmanagementsystemspringboot.Account.Account;
import com.example.bankingmanagementsystemspringboot.Account.AccountRepository;

import com.example.bankingmanagementsystemspringboot.Exception.ResourceNotFoundException;
import com.example.bankingmanagementsystemspringboot.Transactions.DTO.TransactionDTO;
import com.example.bankingmanagementsystemspringboot.Users.Users;
import com.example.bankingmanagementsystemspringboot.Users.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.UUID;


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



    public String generateTransactionId() {

        String transactionId;

        do {

            /* i was looking for a way to avoid duplicate transaction ids and while researching i found uuid as a way to generate unique identifiers uuid is used to generate unique transaction identifiers and prevent possible duplicates that could occur with random numbers */


            transactionId = "TX" + UUID.randomUUID();
        } while (transactionRepository.findByTransactionId(transactionId) != null);

        return transactionId;
    }


    @Transactional
    public String createDeposit(TransactionDTO transactionDTO) {

        String accountNumber = transactionDTO.getAccountNumber();
        BigDecimal amount = transactionDTO.getAmount();


        TransactionRecord transactionRecord = new TransactionRecord();


        if (!validateAccount(accountNumber)) {
                throw new ResourceNotFoundException("Account does not exist");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("The amount must be greater than 0");
        }

        Account account = accountRepository.findByAccountNumber(accountNumber);
        Users user = usersRepository.findByAccountNumber(accountNumber);

        if (user == null) {
                throw new ResourceNotFoundException("User does not exist");
        }

        if (account.credit(amount)) {

            BigDecimal newBalance = account.getBalance();


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
    public String createWithdrawal(TransactionDTO transactionDTO) {


        String accountNumber = transactionDTO.getAccountNumber();
        BigDecimal amount = transactionDTO.getAmount();

        TransactionRecord transactionRecord = new TransactionRecord();


        if (!validateAccount(accountNumber)) {
                throw new ResourceNotFoundException("Account does not exist");

        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("The amount must be greater than 0");

        }

        Account account = accountRepository.findByAccountNumber(accountNumber);
        Users user = usersRepository.findByAccountNumber(accountNumber);

        if (user == null) {
                throw new ResourceNotFoundException("User does not exist");
        }

        if (!account.debit(amount)) {
            throw new IllegalArgumentException("Insufficient funds");


        }

        BigDecimal newBalance = account.getBalance();
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
