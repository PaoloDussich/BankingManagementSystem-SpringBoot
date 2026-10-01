package com.example.bankingmanagementsystemspringboot.Users;

import com.example.bankingmanagementsystemspringboot.SessionContext;
import com.example.bankingmanagementsystemspringboot.Utils;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServices {

    private UsersRepository usersRepository;

    public UserServices(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void createUser(Users users) {


        users.setUserName(users.getUserName());
        users.setPassowordHash(Utils.hashSha(users.getPassowordHash()));
        users.setDefaultPermissions();

        //fileRepository.writeFile(user);,
        usersRepository.save(users);

    }


    public Users findUser(Integer id) {
        return usersRepository.findById(id).orElse(null);
    }

    public String validateUsers(String userName, String password) {
        //Users user = fileRepository.findUsers(userName);

        Users user = usersRepository.findByUserName(userName);


        password = Utils.hashSha(password);

        if (user == null) {
            return "Users not found";
        }

        if (user.isLocked()) {
            return "Locked";
        }


        if (user.getPassowordHash().equals(password)) {
            user.resetFailedAttemps();
            //fileRepository.updateUsers(userName, "0", '5');
            usersRepository.save(user);

            SessionContext.logIn(user);

            return "Login Success";

        } else {

            user.incraseFailedAttemps();
            usersRepository.save(user);


            if (user.getFaildeAtemps() == 3) {
                user.lock();

                usersRepository.save(user);

            }
            return "Wrong password";
        }


    }

    public void deleteUsers(Integer id) {
        Users user = usersRepository.findById(id).orElse(null);
        usersRepository.delete(user);

    }


//    public String updateUser(String userName, String data, char updateOption) {
//
//        Users user = usersRepository.findByUserName(userName);
//
//        if (updateOption == '2') {
//            data = Utils.hashSha(data);
//            user.setPassowordHash(data);
//
//        }
//
//        if (updateOption == '3') {
//            String[] permissions = data.split(",");
//
//
//            for (String permiso : permissions) {
//                permiso = permiso.trim();
//
//
//                try {
//                    Permissions permissions1 = Permissions.valueOf(permiso);
//
//                    user.addPermission(permissions1);
//
//                } catch (IllegalArgumentException error) {
//                    return "Invalid permission: " + permiso;
//
//                }
//            }
//        }
//        usersRepository.save(user);
//        return "Account modified successfully";
//    }

    public  void updateUser(Integer id, Users users){

        Users user = usersRepository.findById(id).orElse(null);

        if (user==null){
            return;
        }

        user.setUserName(users.getUserName());
        user.setPassowordHash(Utils.hashSha(users.getPassowordHash()));
        user.setAccountNumber(users.getAccountNumber());
        user.setRole(users.getRole());

        usersRepository.save(user);


    }


    public ArrayList<Users> showUserList() {
        return new ArrayList<>(usersRepository.findAll());
    }

}
