package com.example.bankingmanagementsystemspringboot.Users.DTO;

import jakarta.validation.constraints.NotBlank;

public class CreateUsersDTO {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

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
