
package com.example.bankingmanagementsystemspringboot.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@JsonPropertyOrder({"id", "userName", "passowordHash", "role", "permissions", "accountNumber", "locked", "faildeAtemps"})
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userName;

    @JsonIgnore
    private String passowordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Permissions> permissions = new HashSet<>();


    private String accountNumber;
    private boolean isLocked = false;
    private int faildeAtemps;

    public Users() {

    }



    public Integer getId(){
        return id;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void lock() {
        isLocked = true;
    }

    public void setLocked(boolean loked) {
        isLocked = loked;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        userName = name;
    }

    public String getPassowordHash() {
        return passowordHash;
    }

    public void setPassowordHash(String password) {
        passowordHash = password;
    }

    public Role getRole() {
        return role;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermission(Set<Permissions> data) {
        this.permissions = data;
    }

    public boolean hasPermission(Permissions permission) {
        return permissions.contains(permission);
    }

    public void addPermission(Permissions permission) {
        permissions.add(permission);
    }

    public void setFaildeAtemps(int atemps) {
        faildeAtemps = atemps;
    }

    public int getFaildeAtemps() {
        return faildeAtemps;
    }

    public void incraseFailedAttemps() {
        faildeAtemps++;
    }

    public void resetFailedAttemps() {
        faildeAtemps = 0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setDefaultPermissions() {
        permissions.addAll(role.getDefaultPermissions());
    }

    public void setRole(Role data) {
        this.role = data;
        permissions.clear();
        setDefaultPermissions();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Users users = (Users) o;

        return isLocked == users.isLocked
                && faildeAtemps == users.faildeAtemps
                && Objects.equals(userName, users.userName)
                && Objects.equals(passowordHash, users.passowordHash)
                && Objects.equals(role, users.role)
                && Objects.equals(permissions, users.permissions)
                && Objects.equals(accountNumber, users.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, passowordHash, role, permissions, accountNumber, isLocked, faildeAtemps);
    }
}

