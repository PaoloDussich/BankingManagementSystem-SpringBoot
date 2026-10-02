package com.example.bankingmanagementsystemspringboot.Users;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/users")
public class UsersControllers {

    private final UserServices userServices;

    public UsersControllers(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping
    public String createUser(@RequestBody Users user) {
     return    userServices.createUser(user);

    }


    //lista completa
    @GetMapping
    public ResponseEntity<ArrayList<Users>> showList() {
        return ResponseEntity.ok(userServices.showUserList());
    }


    //buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Users> showUserForId(@PathVariable Integer id) {

        Users user = userServices.findUser(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        return userServices.deleteUsers(id);
    }



    @PutMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @RequestBody Users users) {
        return userServices.updateUser(id, users);
    }


}
