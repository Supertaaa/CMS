package com.example.SimpleProject.Controller;



import com.example.SimpleProject.DTO.CampaignDTO;
import com.example.SimpleProject.DTO.StaticEmailDTO;
import com.example.SimpleProject.DTO.StaticPhoneDTO;
import com.example.SimpleProject.Entities.*;
import com.example.SimpleProject.Projection.CampaignEmailProjection;
import com.example.SimpleProject.Repository.*;

import com.example.SimpleProject.Service.CampaignService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/Campaign")



//@Controller
public class CampaignController {

    Logger logger = LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    CampaignService campaignService;


    @Autowired
    EmailReposirity emailReposirity;

    @Autowired
    PhoneReposirity phoneReposirity;

    @Autowired
    CampaignReposirity campaignReposirity;

    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    TelcoRepository telcoRepository;


    @GetMapping(path = "/getServiceId")
    public List<Service> getServiceId(){
        return serviceRepository.findAll();
    }


    @GetMapping(path = "/getTelcoId")
    public List<Telco> getTelcoId(){
        return telcoRepository.findAll();
    }


    @PostMapping(path = "/addCampaign")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public Campaign createCampaign(Campaign campaign){
        logger.info("Add campaign " + campaign.getName());
        return campaignService.createCampaign(campaign);
    }

    @PostMapping(path = "/insertData")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String insertData(String idCampaign,MultipartFile file){
        return campaignService.importPhoneEmail(idCampaign, file);
    }

    @PutMapping(path = "/updateCampaign")

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Campaign updateCampaign(Campaign campaign){

        logger.info("Update campaign " + campaign.getName());
        return campaignService.updateCampaign(campaign);
    }

    @DeleteMapping(path = "/delCampaign")

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String delCampaign(int idCampaign){


        logger.info("Delete campaign " + campaignReposirity.findById(idCampaign).get().getName() );
        return campaignService.delCampaign(idCampaign);
    }


    @DeleteMapping(path = "/delCampaigns")
    public String delCampaigns(String[] idCampaigns){

        for(String idCampaign: idCampaigns){
            logger.info("Delete campaign " + campaignReposirity.findById(Integer.parseInt(idCampaign)).get().getName() );
            campaignService.delCampaign(Integer.parseInt(idCampaign));
        }
        return "Done";
    }


    @GetMapping(path = "/getAllCampaign")

    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<Campaign> getAll(){
        return campaignReposirity.findAll();
    }

    @GetMapping(path = "/getCampaignById")

    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Campaign getAll(int id){
        return campaignReposirity.findById(id).get();
    }


    @GetMapping(path = "/getStatic")

    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
//    public List<?> getStatic(int idCampaign, int noOfPage, int noObjectPerPage){
//        int type = campaignReposirity.findById(idCampaign).get().getType();
//        if(type == 0){
//            return campaignReposirity.getEmailStatic(idCampaign, noOfPage, noObjectPerPage);
//        }
//        else if(type == 1){
//            return campaignReposirity.getPhoneStatic(idCampaign, noOfPage, noObjectPerPage);
//        }
//        return null;
//    }



    public List<?> getStatic(int idCampaign){
        if (!campaignReposirity.existsById(idCampaign)){return null;}
        int type = campaignReposirity.findById(idCampaign).get().getType();

        if(type == 0){

            List<StaticEmailDTO> staticEmailDTO = new ArrayList<>();
            Campaign campaign = campaignReposirity.findById(idCampaign).get();
            List<Email> listEmail = emailReposirity.findEmailByCampaignId(idCampaign);
            String service = serviceRepository.findById(campaign.getServiceId()).get().getName();

            for(Email email: listEmail){
                StaticEmailDTO staticEmail = new StaticEmailDTO();
                staticEmail.setEmail(email.getEmail());
                if(email.getStatus() == 0){
                    staticEmail.setStatus("Not Started");
                }
                else if (email.getStatus() == 1){
                    staticEmail.setStatus("Done");
                }
                else if(email.getStatus() == 2){
                    staticEmail.setStatus("Fail");
                }
                else if(email.getStatus() == 3){
                    staticEmail.setStatus("Running");
                }
                staticEmail.setRetry(email.getRepeat());

                staticEmail.setService(service);
                staticEmail.setCreatedDate(email.getCreatedDate());

                staticEmailDTO.add(staticEmail);

            }


            return staticEmailDTO;

        }
        else if(type == 1){

            List<StaticPhoneDTO> staticPhoneDTO = new ArrayList<>();
            Campaign campaign = campaignReposirity.findById(idCampaign).get();
            List<Phone> listPhone = phoneReposirity.findPhoneByCampaignId(idCampaign);
            String service = serviceRepository.findById(campaign.getServiceId()).get().getName();

            for(Phone phone: listPhone){
                StaticPhoneDTO staticPhone = new StaticPhoneDTO();
                staticPhone.setPhone(phone.getPhone());
                if(phone.getStatus() == 0){
                    staticPhone.setStatus("Not Started");
                }
                else if (phone.getStatus() == 1){
                    staticPhone.setStatus("Done");
                }
                else if(phone.getStatus() == 3){
                    staticPhone.setStatus("Running");
                }
                else if(phone.getStatus() == 2){
                    staticPhone.setStatus("Fail");
                }
                staticPhone.setRetry(phone.getRetry());

                staticPhone.setService(service);
                staticPhone.setCreatedDate(phone.getCreatedDate());

                staticPhoneDTO.add(staticPhone);

            }

            return staticPhoneDTO;
        }
        return null;
    }

    @GetMapping(path = "/getCampaignDTO")
    public List<CampaignDTO> getCampaignDTO(){

        List<CampaignDTO> returnList = new ArrayList<>();
        for(Campaign campaign: campaignReposirity.findAll()){

            CampaignDTO campaignDTO = new CampaignDTO(campaign.getName(), campaign.getContent(), null, (campaign.getType() == 1)? "Phone":"Email", serviceRepository.findById(campaign.getServiceId()).get().getName(),telcoRepository.findById(campaign.getTelcoId()).get().getName(), campaign.getRetry(), campaign.getFileName(),campaign.getStartTime(), campaign.getEndTime(), campaign.getVoice());
            campaignDTO.setCreatedDate(campaign.getCreatedDate());
            campaignDTO.setUpdatedDate(campaign.getUpdatedDate());
            if(campaign.getStatus() == 0){
                campaignDTO.setStatus("Not Started");
            }
            else if(campaign.getStatus() == 1){
                campaignDTO.setStatus("Done");
            }
            else if(campaign.getStatus() == 2){
                campaignDTO.setStatus("Fail");
            }
            else if(campaign.getStatus() == 3){
                campaignDTO.setStatus("Running");
            }


//            if(campaign.getType() == 0){
//                boolean notRunning = false;
//                boolean running = false;
//                boolean done = false;
//                boolean fail = false;

//                List<Email> listEmail = emailReposirity.findEmailByCampaignId(campaign.getId());
//
//                for(Email email : listEmail){
//                    if(email.getStatus() == 0){
//                        notRunning = true;
//                    }
//                    else if(email.getStatus() == 1){
//                        done = true;
//                    }
//                    else if(email.getStatus() == 3){
//                        running = true;
//                    }
//                    else if(email.getStatus() == 2){
//                        fail = true;
//                        campaignDTO.setStatus("Fail");
//                        campaign.setStatus(2);
//                        break;
//                    }
//                }
//
//                if(!fail){
//                    if(notRunning && running){
//                        campaignDTO.setStatus("Running");
//                        campaign.setStatus(3);
//                    }
//                    else if(notRunning && done){
//                        campaignDTO.setStatus("Running");
//                        campaign.setStatus(3);
//                    }
//                    else if(running && done){
//                        campaignDTO.setStatus("Running");
//                        campaign.setStatus(3);
//                    }
//                    else if(notRunning && !running && !done){
//                        campaignDTO.setStatus("Not Started");
//                        campaign.setStatus(0);
//                    }
//                    else if(done && !notRunning && !running){
//                        campaignDTO.setStatus("Done");
//                        campaign.setStatus(1);
//                    }
//                    else if(running && !notRunning && !done ){
//                        campaignDTO.setStatus("Running");
//                        campaign.setStatus(3);
//                    }
//                    else{
//                        campaignDTO.setStatus("Not Started");
//                        campaign.setStatus(0);
//                    }
//                }
//
//
//            }
//
//            else if(campaign.getType() == 1){
//                boolean notRunning = false;
//                boolean running = false;
//                boolean done = false;
//                boolean fail = false;
//
//                List<Phone> listPhone = phoneReposirity.findPhoneByCampaignId(campaign.getId());
//
//                for(Phone phone : listPhone){
//                    if(phone.getStatus() == 0){
//                        notRunning = true;
//                    }
//                    else if(phone.getStatus() == 1){
//                        done = true;
//                    }
//                    else if(phone.getStatus() == 2){
//                        fail = true;
//                        campaignDTO.setStatus("Fail");
//                        campaign.setStatus(2);
//                        break;
//                    }
//                    else if(phone.getStatus() == 3){
//                        running = true;
//                    }
//                }
//                if(!fail){
//                    if(notRunning && running){
//                        campaignDTO.setStatus("Running");
//                        campaign.setStatus(3);
//                    }
//                    else if(notRunning && done){
//                        campaignDTO.setStatus("Running");
//                        campaign.setStatus(3);
//                    }
//                    else if(running && done){
//                        campaignDTO.setStatus("Running");
//                        campaign.setStatus(3);
//                    }
//                    else if(notRunning && !running && !done){
//                        campaignDTO.setStatus("Not Started");
//                        campaign.setStatus(0);
//                    }
//                    else if(done && !notRunning && !running){
//                        campaignDTO.setStatus("Done");
//                        campaign.setStatus(1);
//                    }
//                    else if(running && !notRunning && !done ){
//                        campaignDTO.setStatus("Running");
//                        campaign.setStatus(3);
//                    }
//                    else{
//                        campaignDTO.setStatus("Not Started");
//                        campaign.setStatus(0);
//                    }
//                }
//
//            }
//
//            campaignReposirity.save(campaign);
            campaignDTO.setId(campaign.getId());
            returnList.add(campaignDTO);
        }
        return returnList;
    }



}
