package com.example.SimpleProject.Controller;



import com.example.SimpleProject.Entities.Campaign;
import com.example.SimpleProject.Repository.CampaignReposirity;
import com.example.SimpleProject.Service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/Campaign")


//@Controller
public class CampaignController {

    @Autowired
    CampaignService campaignService;


    @Autowired
    CampaignReposirity campaignReposirity;
    @PostMapping(path = "/addCampaign")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public Campaign createCampaign(Campaign campaign, String startTime, String endTime){

        return campaignService.createCampaign(campaign);
    }

    @PostMapping(path = "/insertData")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String insertData(int idCampaign,String nameFile){
        return campaignService.importPhoneEmail(idCampaign, nameFile);
    }

    @PutMapping(path = "/updateCampaign")

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Campaign updateCampaign(Campaign campaign){
        return campaignService.updateCampaign(campaign);
    }

    @DeleteMapping(path = "/delCampaign")

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String delCampaign(@RequestParam int idCampaign){
        return campaignService.delCampaign(idCampaign);
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
    public List<?> getStatic(@RequestParam int idCampaign, @RequestParam int noOfPage, @RequestParam int noObjectPerPage){
        int type = campaignReposirity.findById(idCampaign).get().getType();
        if(type == 0){
            return campaignReposirity.getEmailStatic(idCampaign, noOfPage, noObjectPerPage);
        }
        else if(type == 1){
            return campaignReposirity.getPhoneStatic(idCampaign, noOfPage, noObjectPerPage);
        }
        return null;
    }

}
