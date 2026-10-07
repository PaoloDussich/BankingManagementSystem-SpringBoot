package com.example.bankingmanagementsystemspringboot.Transfers;


import com.example.bankingmanagementsystemspringboot.Account.Account;
import com.example.bankingmanagementsystemspringboot.Account.AccountRepository;

import com.example.bankingmanagementsystemspringboot.Exception.ResourceNotFoundException;
import com.example.bankingmanagementsystemspringboot.Transactions.TransactionRecord;
import com.example.bankingmanagementsystemspringboot.Transactions.TransactionRepository;
import com.example.bankingmanagementsystemspringboot.Transactions.TransactionServices;
import com.example.bankingmanagementsystemspringboot.Transfers.TransferDTO.TransferDTO;
import com.example.bankingmanagementsystemspringboot.Users.Users;
import com.example.bankingmanagementsystemspringboot.Users.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;


@Service
public class TransferServices {

    private final TransferRepository transferRepository;
    private final TransactionServices transactionServices;
    private final TransactionRepository transactionRepository;
    private final UsersRepository usersRepository;
    private final AccountRepository accountRepository;


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
    public String createTransfer(TransferDTO transferDTO) {


        TransferRecord transferRecord = new TransferRecord();

        if (!transactionServices.validateAccount(transferDTO.getSourceAccount())) {
            throw new ResourceNotFoundException("Source account does not exist");

        }

        if (!transactionServices.validateAccount(transferDTO.getTargetAccount())) {
            throw new ResourceNotFoundException("Target account does not exist");

        }

        Users user = usersRepository.findByAccountNumber(transferDTO.getSourceAccount());


        if (user == null) {
            throw new ResourceNotFoundException("User does not exist");

        }

        Account accountOrigin = accountRepository.findByAccountNumber(transferDTO.getSourceAccount());
        Account accountDestiny = accountRepository.findByAccountNumber(transferDTO.getTargetAccount());

        if (transferDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("The amount must be greater than 0");

        } else if (transferDTO.getSourceAccount().equals(transferDTO.getTargetAccount())) {

            throw new IllegalArgumentException("You cannot transfer to the same account");

        } else if (!accountOrigin.debit(transferDTO.getAmount())) {

            throw new IllegalArgumentException("Insufficient funds");

        } else {


            accountDestiny.credit(transferDTO.getAmount());

            BigDecimal sourceBalance = accountOrigin.getBalance();
            BigDecimal targetBalance = accountDestiny.getBalance();


            accountRepository.save(accountOrigin);
            accountRepository.save(accountDestiny);

            TransactionRecord transactionRecordOrigin = new TransactionRecord();
            TransactionRecord transactionRecordDestiny = new TransactionRecord();

            String transactionIdOrigin = transactionServices.generateTransactionId();
            String transactionIdDestiny = transactionServices.generateTransactionId();

            String timeStamp = java.time.LocalDate.now().toString();
            String transferId = generateTransferId();


            transactionRecordOrigin.setTransactionId(transactionIdOrigin);
            transactionRecordOrigin.setAccountNumber(transferDTO.getSourceAccount());
            transactionRecordOrigin.setAmount(transferDTO.getAmount());
            transactionRecordOrigin.setResultingBalance(sourceBalance);
            transactionRecordOrigin.setExecutedBy(user.getUserName());
            transactionRecordOrigin.setType("TRANSFER_OUT");
            transactionRecordOrigin.settImeStamp(timeStamp);


            transactionRecordDestiny.setTransactionId(transactionIdDestiny);
            transactionRecordDestiny.setAccountNumber(transferDTO.getTargetAccount());
            transactionRecordDestiny.setAmount(transferDTO.getAmount());
            transactionRecordDestiny.setResultingBalance(targetBalance);
            transactionRecordDestiny.setExecutedBy(user.getUserName());
            transactionRecordDestiny.setType("TRANSFER_IN");
            transactionRecordDestiny.settImeStamp(timeStamp);


            transferRecord.setTransferId(transferId);
            transferRecord.setSourceAccount(transferDTO.getSourceAccount());
            transferRecord.setTargetAccount(transferDTO.getTargetAccount());
            transferRecord.setAmount(transferDTO.getAmount());
            transferRecord.setTimeStamp(timeStamp);
            transferRecord.setAuthorizedBy(user.getUserName());

// save account transactions
            transactionRepository.save(transactionRecordDestiny);
            transactionRepository.save(transactionRecordOrigin);

// save the transfer record
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
        return new ArrayList<>(transferRepository.findBySourceAccountOrTargetAccount(accountNumber, accountNumber)
        );
    }


}

