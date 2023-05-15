package com.example.SimpleProject.Controller;

import com.example.SimpleProject.DTO.EmailDTO;
import com.example.SimpleProject.Entities.Email;
import com.example.SimpleProject.Repository.CampaignReposirity;
import com.example.SimpleProject.Repository.EmailReposirity;
import com.example.SimpleProject.Repository.ServiceRepository;
import com.example.SimpleProject.Repository.TelcoRepository;
import com.example.SimpleProject.Service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/email")
public class EmailController {

    Logger logger = LoggerFactory.getLogger(EmailController.class);
    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    TelcoRepository telcoRepository;
    @Autowired
    EmailService emailService;

    @Autowired
    EmailReposirity emailReposirity;

    @Autowired
    CampaignReposirity campaignReposirity;

    @PostMapping(path = "/addEmail")
    public Email addEmail(Email email){
        logger.info("Add email " + email.getEmail());
        return emailService.createEmail(email);
    }

    @PutMapping(path = "/updateEmail")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public Email updateEmail(Email email){

        logger.info("Update email from " + emailReposirity.findById(email.getId()).get().getEmail() + " to " + email.getEmail());
        return emailService.updateEmail(email);
    }

    @DeleteMapping(path = "/delEmail")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public String delEmail(int idEmail){

        logger.info("Delete email " + emailReposirity.findById(idEmail).get().getEmail());
        return emailService.delEmail(idEmail);
    }

    @DeleteMapping(path = "/delEmails")
    public String delEmails(String[] idEmails){

        for(String idEmail: idEmails){

            logger.info("Delete email " + emailReposirity.findById(Integer.parseInt(idEmail)).get().getEmail());
            emailService.delEmail(Integer.parseInt(idEmail));
        }
        return "Done";
    }


    @GetMapping(path = "/emailById")
    public Email getEmailById(int idEmail){
        return emailReposirity.findById(idEmail).get();
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


    @GetMapping(path = "/getEmailDTO")
    public List<EmailDTO> getEmailDTO(){
        List<EmailDTO> returnList = new ArrayList<>();

        for(Email email: emailReposirity.findAll()){
            EmailDTO emailDTO = new EmailDTO(null,email.getEmail(), email.getRepeat(),null, campaignReposirity.findById(email.getCampaignId()).get().getName());
            emailDTO.setCreatedDate(email.getCreatedDate());
            emailDTO.setUpdatedDate(email.getUpdatedDate());
            emailDTO.setId(email.getId());
            emailDTO.setServiceId(serviceRepository.findById(campaignReposirity.findById(email.getCampaignId()).get().getServiceId()).get().getName());

            if(email.getStatus() == 0){
                emailDTO.setStatus("Not Started");
            }
            else if(email.getStatus() == 1){
                emailDTO.setStatus("Done");
            }
            else if(email.getStatus() == 3){
                emailDTO.setStatus("Running");
            }
            else if(email.getStatus() == 2){
                emailDTO.setStatus("Fail");
            }

            returnList.add(emailDTO);
        }
        return returnList;
    }


}
