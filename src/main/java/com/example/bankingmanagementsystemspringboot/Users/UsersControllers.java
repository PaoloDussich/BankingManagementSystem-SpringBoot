package com.example.bankingmanagementsystemspringboot.Users;
import com.example.bankingmanagementsystemspringboot.Users.DTO.CreateUsersDTO;
import com.example.bankingmanagementsystemspringboot.Users.DTO.LoginDTO;
import com.example.bankingmanagementsystemspringboot.Users.DTO.UpdateUserDTO;
import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@RestController
@RequestMapping("api/v1/users")
public class UsersControllers {

    private final UserServices userServices;

    public UsersControllers(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping
    public String createUser(@Valid @RequestBody CreateUsersDTO user) {
        return userServices.createUser(user);

    }


    @GetMapping
    public ResponseEntity<ArrayList<Users>> showList() {
        return ResponseEntity.ok(userServices.showUserList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> showUserForId(@PathVariable Integer id) {

        Users user = userServices.findUser(id);

        return ResponseEntity.ok(user);
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {

        return userServices.deleteUsers(id);



    }


    @PutMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @RequestBody UpdateUserDTO users) {
        return userServices.updateUser(id, users);
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginDTO loginDTO,
            HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response) {

        Users userValidate = userServices.loginValidation(loginDTO);

        request.changeSessionId();

        session.setAttribute("User", userValidate);

        var authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        userValidate.getUserName(),
                        null,
                        AuthorityUtils.NO_AUTHORITIES
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        HttpSessionSecurityContextRepository repository =
                new HttpSessionSecurityContextRepository();

        repository.saveContext(context, request, response);

        return ResponseEntity.ok("Login successfully");
    }


    @PostMapping("/logout")
    public String logOut( HttpSession session) {

    session.invalidate();
        return "Logout successfully";
    }


    @GetMapping("/session")
    public Users getUser(HttpSession session){
         return (Users)  session.getAttribute("User");

    }



}
