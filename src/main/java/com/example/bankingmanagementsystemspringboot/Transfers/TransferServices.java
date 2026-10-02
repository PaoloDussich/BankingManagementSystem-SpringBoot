package com.example.bankingmanagementsystemspringboot.Transfers;


import com.example.bankingmanagementsystemspringboot.Account.Account;
import com.example.bankingmanagementsystemspringboot.Account.AccountRepository;

import com.example.bankingmanagementsystemspringboot.Transactions.TransactionRecord;
import com.example.bankingmanagementsystemspringboot.Transactions.TransactionRepository;
import com.example.bankingmanagementsystemspringboot.Transactions.TransactionServices;
import com.example.bankingmanagementsystemspringboot.Users.Users;
import com.example.bankingmanagementsystemspringboot.Users.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Random;


@Service
public class TransferServices {

    private TransferRepository transferRepository;
    private TransactionServices transactionServices;
    private TransactionRepository transactionRepository;
    private UsersRepository usersRepository;
    private AccountRepository accountRepository;


    public TransferServices(TransactionServices transactionServices, TransferRepository transferRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, UsersRepository usersRepository) {

        this.transactionServices = transactionServices;
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.usersRepository = usersRepository;


    }

    private final Random random = new Random();

    public String generateTransferId() {

        String transferId;

        do {
            transferId = "TR" + random.nextInt(100000);
        } while (transferRepository.findByTransferId(transferId) != null);

        return transferId;
    }

    @Transactional
    public String createTransfer(TransferRecord transferRecord) {

        if (!transactionServices.validateAccount(transferRecord.getSourceAccount())) {
            return "Source account does not exist";

        }

        if (!transactionServices.validateAccount(transferRecord.getTargetAccount())) {
            return "Target account does not exist";

        }

        Users user = usersRepository.findByAccountNumber(transferRecord.getSourceAccount());


        if (user == null) {
            return "User does not exist";
        }

        Account accountOrigin = accountRepository.findByAccountNumber(transferRecord.getSourceAccount());
        Account accountDestiny = accountRepository.findByAccountNumber(transferRecord.getTargetAccount());

        if (transferRecord.getAmount() <= 0) {
            return "The amount must be greater than 0";

        } else if (transferRecord.getSourceAccount().equals(transferRecord.getTargetAccount())) {

            return "You cannot transfer to the same account";

        } else if (!accountOrigin.debit(transferRecord.getAmount())) {

            return "Insufficient funds";

        } else {


            accountDestiny.credit(transferRecord.getAmount());

            double sourceBalance = accountOrigin.getBalance();
            double targetBalance = accountDestiny.getBalance();


            accountRepository.save(accountOrigin);
            accountRepository.save(accountDestiny);

            TransactionRecord transactionRecordOrigin = new TransactionRecord();
            TransactionRecord transactionRecordDestiny = new TransactionRecord();

            String transactionID = transactionServices.generateTransactionId();
            String timeStamp = java.time.LocalDateTime.now().toString();
            String transferId = generateTransferId();


            transactionRecordOrigin.setTransactionId(transactionID);
            transactionRecordOrigin.setAccountNumber(transferRecord.getSourceAccount());
            transactionRecordOrigin.setAmount(transferRecord.getAmount());
            transactionRecordOrigin.setResultingBalance(sourceBalance);
            transactionRecordOrigin.setExecutedBy(user.getUserName());
            transactionRecordOrigin.setType("TRANSFER_OUT");
            transactionRecordOrigin.settImeStamp(timeStamp);


            transactionRecordDestiny.setTransactionId(transactionID);
            transactionRecordDestiny.setAccountNumber(transferRecord.getTargetAccount());
            transactionRecordDestiny.setAmount(transferRecord.getAmount());
            transactionRecordDestiny.setResultingBalance(targetBalance);
            transactionRecordDestiny.setExecutedBy(user.getUserName());
            transactionRecordDestiny.setType("TRANSFER_IN");
            transactionRecordDestiny.settImeStamp(timeStamp);


            transferRecord.setTransferId(transferId);
            transferRecord.setTimeStamp(timeStamp);
            transferRecord.setAuthorizedBy(user.getUserName());

// Save account transactions
            transactionRepository.save(transactionRecordDestiny);
            transactionRepository.save(transactionRecordOrigin);


// Save the transfer record
            transferRepository.save(transferRecord);
            return "Transfer completed successfully";


        }
    }


    public ArrayList<TransferRecord> showHistory() {
        return new ArrayList<>(transferRepository.findAll());
    }


    public TransferRecord findTransaction(String transferId) {
        return transferRepository.findByTransferId(transferId);
    }

    public ArrayList<TransferRecord> showHistoryAboutOneAccount(String accountNumber) {
        return new ArrayList<>(transferRepository.findBySourceAccountOrTargetAccount(accountNumber,accountNumber)
        );
    }


}

