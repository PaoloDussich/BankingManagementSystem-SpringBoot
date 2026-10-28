//package com.example.bankingmanagementsystemspringboot.config;
//
//import com.example.bankingmanagementsystemspringboot.Users.Role;
//import com.example.bankingmanagementsystemspringboot.Users.Users;
//import com.example.bankingmanagementsystemspringboot.Users.UsersRepository;
//import com.example.bankingmanagementsystemspringboot.Utils;
//
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//
//
//
//@Component
//public class AdminBootstrap implements ApplicationRunner {
//
//    private final UsersRepository usersRepository;
//
//    public AdminBootstrap(UsersRepository usersRepository) {
//        this.usersRepository = usersRepository;
//    }
//
//
//
//    @Override
//    public void run(ApplicationArguments args) {
//
//        boolean adminExists = usersRepository.findAll().stream().anyMatch(user -> user.getRole() == Role.ADMIN);
//
//        if (adminExists) {
//
//            return;
//        }
//
//        String username = System.getenv("BMS_ADMIN_USERNAME");
//        String password = System.getenv("BMS_ADMIN_PASSWORD");
//
//        if (username == null || username.isBlank()
//                || password == null || password.length() < 12) {
//            throw new IllegalStateException(
//                    "Initial administrator credentials are missing or invalid"
//            );
//        }
//
//        if (usersRepository.findByUserName(username) != null) {
//            throw new IllegalStateException(
//                    "Administrator username is already in use"
//            );
//        }
//
//        Users admin = new Users();
//
//        admin.setUserName(username);
//        admin.setPassowordHash(Utils.hashPassword(password));
//        admin.setRole(Role.ADMIN);
//
//        usersRepository.save(admin);
//
//        System.out.println("Initial administrator created");
//    }
//}