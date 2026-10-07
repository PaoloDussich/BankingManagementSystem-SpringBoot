package com.example.bankingmanagementsystemspringboot;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




public class Utils {

    //hash de la password
    public static String hashPassword(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);



    }

}
