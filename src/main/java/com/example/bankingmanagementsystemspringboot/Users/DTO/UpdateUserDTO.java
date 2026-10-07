package com.example.bankingmanagementsystemspringboot.Users.DTO;

public class UpdateUserDTO {
    private String userName;
    private  String password;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
