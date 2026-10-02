package com.example.bankingmanagementsystemspringboot.Users;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer > {

    Users findByUserName(String userName);
    Users findByAccountNumber(String accountNumber);


}
