package com.example.bankingmanagementsystemspringboot.Account.DTO;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class CreateAccountDTO {

    @NotBlank
    private String pin;

    @NotBlank
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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CreateAccountDTO that = (CreateAccountDTO) o;
        return Objects.equals(pin, that.pin) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pin, phone);
    }
}
