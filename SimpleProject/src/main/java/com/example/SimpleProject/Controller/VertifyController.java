package com.example.SimpleProject.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping(path = "/api/v1")
public class VertifyController {
    public int randomCode;
    @GetMapping(path = "/sendVertify")
    public String sendVertifyCode(){
        Random rnd = new Random();
        randomCode = 100000 + rnd.nextInt(900000);
        return Integer.toString(randomCode);
    }
    @PostMapping(path = "/vertify")
    public Boolean vertifyCode(String code){
        return Objects.equals(code, Integer.toString(randomCode));
    }
}
