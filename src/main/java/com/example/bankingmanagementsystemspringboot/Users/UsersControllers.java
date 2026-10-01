package com.example.bankingmanagementsystemspringboot.Users;


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
    public void createUser(@RequestBody Users user) {
        userServices.createUser(user);

    }


    //lista completa
    @GetMapping
    public ArrayList<Users> mostarListaCompleta() {
        return userServices.showUserList();
    }

    //buscar por id
    @GetMapping("/{id}")
    public Users mostarUsuarioPorId(@PathVariable Integer id) {
        return userServices.findUser(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarUser(@PathVariable Integer id) {
        userServices.deleteUsers(id);
    }

    @PutMapping("/{id}")
    public void modificarUser(@PathVariable Integer id ,@RequestBody Users users) {
        userServices.updateUser(id, users);
    }


}
