package com.example.bankingmanagementsystemspringboot;

import com.example.bankingmanagementsystemspringboot.Users.Users;

public class SessionContext {
    private static Users currentUser;

    public static void logIn( Users user){
        currentUser = user;
    }

    public static Users getCurrentUser(){
        return currentUser;
    }

    public static void logout(){
        currentUser = null;
    }
}
