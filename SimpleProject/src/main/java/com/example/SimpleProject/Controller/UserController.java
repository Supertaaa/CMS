package com.example.SimpleProject.Controller;


import com.example.SimpleProject.Entities.User;
import com.example.SimpleProject.Repository.UserRepository;
//import com.example.SimpleProject.User.UserService;
import com.example.SimpleProject.Service.ServiceImpl.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/user")
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

//    @Autowired
//    UserService userService;

    @PostMapping(path = "/createUser")
    public String createUser(User user){
        if (userRepository.existsUserByUserName(user.getUserName())){return null;}
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        newUser.setRole("USER");
        userRepository.save(newUser);
        return TokenAuthenticationService.getToken(newUser.getUserName());
    }

    @PostMapping(path = "/login")
    public String login(User user){
        if (userRepository.existsUserByUserName(user.getUserName()) == false){return null;}
        User u = userRepository.findByUserName(user.getUserName()); //gets user object

        String password = u.getPassword();
        if (BCrypt.checkpw(user.getPassword(), password) == false){
            return null;
        }
        return TokenAuthenticationService.getToken(user.getUserName());
    }
}
