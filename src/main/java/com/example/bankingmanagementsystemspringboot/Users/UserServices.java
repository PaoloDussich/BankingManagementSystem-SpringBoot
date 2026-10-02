package com.example.bankingmanagementsystemspringboot.Users;
import com.example.bankingmanagementsystemspringboot.Account.Account;
import com.example.bankingmanagementsystemspringboot.Account.AccountRepository;
import com.example.bankingmanagementsystemspringboot.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserServices {

    private UsersRepository usersRepository;
    private AccountRepository accountRepository;

    public UserServices(UsersRepository usersRepository, AccountRepository accountRepository) {
        this.usersRepository = usersRepository;
        this.accountRepository= accountRepository;
    }

    public String createUser(Users users) {

        users.setPassowordHash(Utils.hashSha(users.getPassowordHash()));
        usersRepository.save(users);
        return "User created successfully";

    }


    public Users findUser(Integer id) {
        return usersRepository.findById(id).orElse(null);
    }

@Transactional
    public String deleteUsers(Integer id) {
        Users user = usersRepository.findById(id).orElse(null);

        if (user == null) {
            return "User doesn't exist";
        }

        if (user.getAccountNumber() != null) {
            Account account = accountRepository.findByAccountNumber(user.getAccountNumber());

            if (account != null && account.getBalance() > 0) {
                return "User cannot be deleted because the account has funds";
            }

            if (account != null) {
                accountRepository.delete(account);
            }
        }
        usersRepository.delete(user);
        return "User deleted successfully";

    }


    public String updateUser(Integer id, Users users){
        Users user = usersRepository.findById(id).orElse(null);

        if (user == null) {
            return "User doesn't exist";
        }



        user.setUserName(users.getUserName());
        user.setPassowordHash(Utils.hashSha(users.getPassowordHash()));
        user.setAccountNumber(users.getAccountNumber());
        user.setRole(users.getRole());

        usersRepository.save(user);
        return "User updated successfully";


    }


    public ArrayList<Users> showUserList() {
        return new ArrayList<>(usersRepository.findAll());
    }

}
