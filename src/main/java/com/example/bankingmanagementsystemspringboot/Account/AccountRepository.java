package com.example.bankingmanagementsystemspringboot.Account;

import com.example.bankingmanagementsystemspringboot.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByAccountNumber(String accountNumber);


}
