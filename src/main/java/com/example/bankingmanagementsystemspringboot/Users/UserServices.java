package com.example.bankingmanagementsystemspringboot.Users;

import com.example.bankingmanagementsystemspringboot.Account.Account;
import com.example.bankingmanagementsystemspringboot.Account.AccountRepository;
import com.example.bankingmanagementsystemspringboot.Exception.ResourceNotFoundException;
import com.example.bankingmanagementsystemspringboot.Exception.UnauthorizedException;
import com.example.bankingmanagementsystemspringboot.Users.DTO.CreateUsersDTO;
import com.example.bankingmanagementsystemspringboot.Users.DTO.LoginDTO;
import com.example.bankingmanagementsystemspringboot.Users.DTO.UpdateUserDTO;
import com.example.bankingmanagementsystemspringboot.Utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class UserServices {

    private final UsersRepository usersRepository;
    private final AccountRepository accountRepository;

    public UserServices(UsersRepository usersRepository, AccountRepository accountRepository) {
        this.usersRepository = usersRepository;
        this.accountRepository = accountRepository;
    }

    public String createUser(CreateUsersDTO users) {

        Users user = new Users();


        user.setUserName(users.getUserName());
        user.setPassowordHash(Utils.hashPassword(users.getPassword()));
        user.setRole(Role.CLIENT);



        usersRepository.save(user);
        return "User created successfully";
    }


    public Users findUser(Integer id) {
        return usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));

    }



    @Transactional
    public String deleteUsers(Integer id) {
        Users user = usersRepository.findById(id).orElse(null);

        if (user == null) {

            throw new ResourceNotFoundException("User doesn't exist");

        }

        if (user.getAccountNumber() != null) {
            Account account = accountRepository.findByAccountNumber(user.getAccountNumber());

            if (account != null && account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                throw new IllegalArgumentException( "User cannot be deleted because the account has funds");
            }

            if (account != null) {
                accountRepository.delete(account);
            }
        }
        usersRepository.delete(user);
        return "User deleted successfully";

    }


    public String updateUser(Integer id, UpdateUserDTO users) {
        Users user = usersRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User doesn't exist");
        }

        if (users.getUserName() != null){
            user.setUserName(users.getUserName());
        }

        if (users.getPassword() != null){
            user.setPassowordHash(Utils.hashPassword(users.getPassword()));
        }



        usersRepository.save(user);
        return "User updated successfully";


    }


    public ArrayList<Users> showUserList() {
        return new ArrayList<>(usersRepository.findAll());
    }


    public Users loginValidation(LoginDTO loginDTO) {

        Users usuarioEncontrado = usersRepository.findByUserName(loginDTO.getUserName());

        if (usuarioEncontrado != null && new BCryptPasswordEncoder().matches(loginDTO.getPassword(), usuarioEncontrado.getPassowordHash())) {
            return usuarioEncontrado;
        }

        throw new UnauthorizedException("User or password is wrong");

    }

}
