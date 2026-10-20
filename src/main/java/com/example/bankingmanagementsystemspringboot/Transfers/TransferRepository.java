package com.example.bankingmanagementsystemspringboot.Transfers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<TransferRecord, Integer> {
    TransferRecord findByTransferId(String transferId);
    TransferRecord findByIdempotencyKey(String idempotencyKey);

    List<TransferRecord> findBySourceAccountOrTargetAccount(String sourceAccount, String targetAccount);


}
