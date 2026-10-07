package com.example.bankingmanagementsystemspringboot.Account.DTO;

public class UpdateAccountDTO {

    private String pin;
    private String phone;


    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
