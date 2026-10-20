package com.example.bankingmanagementsystemspringboot.Transfers;


import com.example.bankingmanagementsystemspringboot.Account.Account;
import com.example.bankingmanagementsystemspringboot.Account.AccountRepository;

import com.example.bankingmanagementsystemspringboot.Exception.IdempotencyConflictException;
import com.example.bankingmanagementsystemspringboot.Exception.ResourceNotFoundException;
import com.example.bankingmanagementsystemspringboot.Exception.UnsupportedOperationException;
import com.example.bankingmanagementsystemspringboot.Transactions.TransactionRecord;
import com.example.bankingmanagementsystemspringboot.Transactions.TransactionRepository;
import com.example.bankingmanagementsystemspringboot.Transactions.TransactionServices;
import com.example.bankingmanagementsystemspringboot.Transfers.TransferDTO.TransferDTO;
import com.example.bankingmanagementsystemspringboot.Users.Users;
import com.example.bankingmanagementsystemspringboot.Users.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    private final IbanValidationService ibanValidationService;
    @PersistenceContext
    private EntityManager entityManager;



    public TransferServices(TransactionServices transactionServices, TransferRepository transferRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, UsersRepository usersRepository, IbanValidationService ibanValidationService, EntityManager entityManager) {

        this.transactionServices = transactionServices;
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.usersRepository = usersRepository;
        this.ibanValidationService = ibanValidationService;
        this.entityManager = entityManager;



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
    public String createTransfer(TransferDTO transferDTO, String idempotencyKey) {

        if (transferDTO == null) {

            throw new IllegalArgumentException("Transfer data is required");
        }



        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException("Idempotency-Key header is required");

        }



        String sourceAccount = transferDTO.getSourceAccount();
        String targetAccount = transferDTO.getTargetAccount();
        BigDecimal amount = transferDTO.getAmount();



        if (sourceAccount == null || sourceAccount.isBlank()) {

            throw new IllegalArgumentException("Source account is required");
        }

        if (targetAccount == null || targetAccount.isBlank()) {

            throw new IllegalArgumentException("Target account is required");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException("The amount must be greater than 0");
        }

        if (sourceAccount.equals(targetAccount)) {

            throw new IllegalArgumentException("You cannot transfer to the same account");
        }

        entityManager.createNativeQuery("SELECT pg_advisory_xact_lock(hashtext(:key)::bigint)").setParameter("key", idempotencyKey).getSingleResult();


        TransferRecord existingTransfer = transferRepository.findByIdempotencyKey(idempotencyKey);

        if (existingTransfer != null) {
            boolean sameRequest = existingTransfer.getSourceAccount().equals(transferDTO.getSourceAccount())  && existingTransfer.getTargetAccount().equals(transferDTO.getTargetAccount())  && existingTransfer.getAmount().compareTo(transferDTO.getAmount()) == 0;

            if (!sameRequest) {
                throw new IdempotencyConflictException("This Idempotency-Key was already used for a different transfer");

            }

            return "Transfer already processed. Transfer ID: "  + existingTransfer.getTransferId();
        }

        Account accountOrigin = accountRepository.findByAccountNumber(sourceAccount);


        if (accountOrigin == null) {
            throw new ResourceNotFoundException("Source account does not exist");

        }


     Account accountDestiny;

        if (ibanValidationService.isValidIban(targetAccount)){
            throw new UnsupportedOperationException("International transfers are not supported yet");

        }else if(targetAccount.matches("ACC[A-Za-z0-9]+")){
            accountDestiny = accountRepository.findByAccountNumber(targetAccount);

            if (accountDestiny == null){
                throw new ResourceNotFoundException("Target account does not exist");
            }

        }else {
            throw new IllegalArgumentException("Destiny must be a valid Iban or local account number");
        }



        Users user = usersRepository.findByAccountNumber(sourceAccount);

        if (user == null) {

            throw new ResourceNotFoundException("User does not exist");
        }


        accountOrigin.debit(amount);
        accountDestiny.credit(amount);

        BigDecimal sourceBalance = accountOrigin.getBalance();
        BigDecimal targetBalance = accountDestiny.getBalance();

        accountRepository.save(accountOrigin);
        accountRepository.save(accountDestiny);

        String timeStamp = java.time.LocalDateTime.now().toString();
        String transferId = generateTransferId();

        TransactionRecord transactionRecordOrigin = new TransactionRecord();
        TransactionRecord transactionRecordDestiny = new TransactionRecord();

        transactionRecordOrigin.setTransactionId(transactionServices.generateTransactionId());
        transactionRecordOrigin.setAccountNumber(sourceAccount);
        transactionRecordOrigin.setAmount(amount);
        transactionRecordOrigin.setResultingBalance(sourceBalance);
        transactionRecordOrigin.setExecutedBy(user.getUserName());
        transactionRecordOrigin.setType("TRANSFER_OUT");
        transactionRecordOrigin.settImeStamp(timeStamp);

        transactionRecordDestiny.setTransactionId(transactionServices.generateTransactionId());
        transactionRecordDestiny.setAccountNumber(targetAccount);
        transactionRecordDestiny.setAmount(amount);
        transactionRecordDestiny.setResultingBalance(targetBalance);
        transactionRecordDestiny.setExecutedBy(user.getUserName());
        transactionRecordDestiny.setType("TRANSFER_IN");
        transactionRecordDestiny.settImeStamp(timeStamp);

        TransferRecord transferRecord = new TransferRecord();
        transferRecord.setTransferId(transferId);
        transferRecord.setSourceAccount(sourceAccount);
        transferRecord.setTargetAccount(targetAccount);
        transferRecord.setAmount(amount);
        transferRecord.setTimeStamp(timeStamp);
        transferRecord.setAuthorizedBy(user.getUserName());
        transferRecord.setIdempotencyKey(idempotencyKey);

        transactionRepository.save(transactionRecordOrigin);
        transactionRepository.save(transactionRecordDestiny);
        transferRepository.saveAndFlush(transferRecord);

        return "Transfer completed successfully";
    }


    public ArrayList<TransferRecord> showHistory() {
        return new ArrayList<>(transferRepository.findAll());
    }


    public TransferRecord findTransaction(String transferId) {
        return transferRepository.findByTransferId(transferId);
    }

    public ArrayList<TransferRecord> showHistoryAboutOneAccount(String accountNumber) {

        return new ArrayList<>(transferRepository.findBySourceAccountOrTargetAccount(accountNumber, accountNumber));

    }


}

