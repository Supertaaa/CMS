package com.example.SimpleProject.Controller;



import com.example.SimpleProject.Entities.Phone;
import com.example.SimpleProject.Repository.PhoneReposirity;
import com.example.SimpleProject.Service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/phone")
public class PhoneController {
    @Autowired
    PhoneService phoneService;

    @Autowired
    PhoneReposirity phoneReposirity;


    @PostMapping(path = "/addPhone")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public Phone addPhone(Phone phone){
        return phoneService.createPhone(phone);
    }

    @PutMapping(path = "/updatePhone")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Phone updatePhone(Phone phone){
        return phoneService.updatePhoone(phone);
    }

    @DeleteMapping(path = "/delPhone")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public String delPhone(int idPhone){
        return phoneService.delPhone(idPhone);
    }


    @GetMapping(path = "/getAll")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<Phone> getAll(){
        return phoneReposirity.findAll();
    }

    @GetMapping(path = "/getById")

    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Phone getById(int id){
        return phoneReposirity.findById(id).get();
    }




}
