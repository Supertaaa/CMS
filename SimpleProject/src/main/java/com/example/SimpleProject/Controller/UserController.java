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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RequestMapping(path = "/user")
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

//    @Autowired
//    UserService userService;



    @GetMapping(path = "/getRole")
    public String getUsername(String userName) {
       return userRepository.findByUserName(userName).getRole();
    }

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
    public String login(HttpServletResponse response, User user){
        if (!userRepository.existsUserByUserName(user.getUserName())){return null;}
        User u = userRepository.findByUserName(user.getUserName()); //gets user object

        String password = u.getPassword();
        if (!BCrypt.checkpw(user.getPassword(), password)){
            return null;
        }

        ResponseCookie cookie = ResponseCookie.from("ROLE", u.getRole()) // key & value
                .httpOnly(true)
                .secure(false)
                //    .domain("localhost")  // host
                //    .path("/")      // path
                .sameSite("None")  // sameSite
                .build()
                ;

        // Response to the client
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Set-Cookie");



        // Response to the client
        //response.addCookie(new Cookie("ROLE", u.getRole()));
        return TokenAuthenticationService.getToken(user.getUserName());
    }
}
