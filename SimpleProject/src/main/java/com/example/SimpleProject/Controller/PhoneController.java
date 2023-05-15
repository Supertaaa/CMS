package com.example.SimpleProject.Controller;



import com.example.SimpleProject.DTO.PhoneDTO;
import com.example.SimpleProject.Entities.Phone;
import com.example.SimpleProject.Repository.CampaignReposirity;
import com.example.SimpleProject.Repository.PhoneReposirity;
import com.example.SimpleProject.Repository.ServiceRepository;
import com.example.SimpleProject.Repository.TelcoRepository;
import com.example.SimpleProject.Service.PhoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/phone")
public class PhoneController {

    Logger logger = LoggerFactory.getLogger(PhoneController.class);
    @Autowired
    CampaignReposirity campaignReposirity;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    TelcoRepository telcoRepository;


    @Autowired
    PhoneService phoneService;

    @Autowired
    PhoneReposirity phoneReposirity;


    @PostMapping(path = "/addPhone")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public Phone addPhone(Phone phone){

        logger.info("Add phone " + phone.getPhone());
        return phoneService.createPhone(phone);
    }

    @PutMapping(path = "/updatePhone")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Phone updatePhone(Phone phone){

        logger.info("Update phone from " + phoneReposirity.findById(phone.getId()).get().getPhone() + " to " + phone.getPhone());
        return phoneService.updatePhoone(phone);
    }

    @DeleteMapping(path = "/delPhone")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public String delPhone(int idPhone){

        logger.info("Delete phone " + phoneReposirity.findById(idPhone).get().getPhone());
        return phoneService.delPhone(idPhone);
    }

    @DeleteMapping(path = "/delPhones")
    public String delPhones(String[] idPhones){

        for(String idPhone: idPhones){
            logger.info("Delete phone " + phoneReposirity.findById(Integer.parseInt(idPhone)).get().getPhone());
            phoneService.delPhone(Integer.parseInt(idPhone));
        }
        return "Done";
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


    @GetMapping(path = "/getPhoneDTO")
    public List<PhoneDTO> getPhoneDTO(){
        List<PhoneDTO> returnList = new ArrayList<>();
        for(Phone phone : phoneReposirity.findAll()){
            PhoneDTO phoneDTO = new PhoneDTO(null, phone.getPhone(), null,null,phone.getRetry(),campaignReposirity.findById(phone.getCampaignId()).get().getName());
            phoneDTO.setCreatedDate(phone.getCreatedDate());
            phoneDTO.setUpdatedDate(phone.getUpdatedDate());
            phoneDTO.setId(phone.getId());
            phoneDTO.setServiceId(serviceRepository.findById(campaignReposirity.findById(phone.getCampaignId()).get().getServiceId()).get().getName());
            phoneDTO.setTelcoId(telcoRepository.findById(campaignReposirity.findById(phone.getCampaignId()).get().getTelcoId()).get().getName());
            if(phone.getStatus() == 0){
                phoneDTO.setStatus("Not Started");
            }
            else if(phone.getStatus() == 1){
                phoneDTO.setStatus("Done");
            }
            else if(phone.getStatus() == 2){
                phoneDTO.setStatus("Fail");
            }
            else if(phone.getStatus() == 3){
                phoneDTO.setStatus("Running");
            }
            returnList.add(phoneDTO);
        }
        return  returnList;
    }




}
