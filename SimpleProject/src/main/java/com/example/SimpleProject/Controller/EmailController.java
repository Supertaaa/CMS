package com.example.SimpleProject.Controller;

import com.example.SimpleProject.Entities.Email;
import com.example.SimpleProject.Repository.EmailReposirity;
import com.example.SimpleProject.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/email")
public class EmailController {


    @Autowired
    EmailService emailService;

    @Autowired
    EmailReposirity emailReposirity;

    @PostMapping(path = "/addEmail")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public Email addEmail(Email email){
        System.out.println(email.getEmail());
        return emailService.createEmail(email);
    }

    @PutMapping(path = "/updateEmail")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public Email updateEmail(Email email){
        return emailService.updateEmail(email);
    }

    @DeleteMapping(path = "/delEmail")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public String delEmail(int idEmail){
        return emailService.delEmail(idEmail);
    }

    @GetMapping(path = "/getEmail")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    //@PreAuthorize("hasAuthority('ROLE_USER')")

    public List<Email> addEmail(){
        return emailReposirity.findAll();
    }

    @GetMapping(path = "/getEmailById")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")

    public Email addEmail(int id){
        return emailReposirity.findById(id).get();
    }


}
