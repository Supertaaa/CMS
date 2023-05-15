package com.example.SimpleProject.Controller;


import com.example.SimpleProject.Entities.User;
import com.example.SimpleProject.Repository.UserRepository;
//import com.example.SimpleProject.User.UserService;
import com.example.SimpleProject.Service.ServiceImpl.TokenAuthenticationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.List;


@RequestMapping(path = "/user")
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;


    @Autowired
    private PasswordEncoder encoder;

    @GetMapping(path = "/getRole")
    public String getRole(String token) {

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        return userRepository.findByUserName(TokenAuthenticationService.GetRoleFromSub(payload)).getRole();
    }

    @GetMapping(path = "/getUserName")
    public String getUsername(String token) {

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        return TokenAuthenticationService.GetRoleFromSub(payload);
    }

    @PostMapping(path = "/createUser")
    public String createUser(User user){
        if (userRepository.existsUserByUserName(user.getUserName())){return "User Existed";}
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRole("USER");
        userRepository.save(newUser);
        return TokenAuthenticationService.getToken(newUser.getUserName());
    }

    @PostMapping(path = "/login")
    public String login( User user){
        if (!userRepository.existsUserByUserName(user.getUserName())){return null;}
        User u = userRepository.findByUserName(user.getUserName()); //gets user object
        String password = u.getPassword();
        if (!BCrypt.checkpw(user.getPassword(), password)){
            return null;
        }
        return TokenAuthenticationService.getToken(user.getUserName());
    }
}
