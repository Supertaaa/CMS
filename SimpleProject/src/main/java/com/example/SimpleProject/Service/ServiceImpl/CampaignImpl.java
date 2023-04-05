package com.example.SimpleProject.Service.ServiceImpl;

import com.example.SimpleProject.Entities.Campaign;
import com.example.SimpleProject.Entities.Email;
import com.example.SimpleProject.Entities.Phone;
import com.example.SimpleProject.Repository.*;


import com.example.SimpleProject.Service.CampaignService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
public class CampaignImpl implements CampaignService {
    @Autowired
    CampaignReposirity campaignReposirity;

    @Autowired
    EmailReposirity emailReposirity;

    @Autowired
    PhoneReposirity phoneReposirity;



    @Override
    public Campaign createCampaign(Campaign campaign) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        System.out.println(campaign.getName());

        System.out.println(campaign.getStartTime());
        System.out.println(campaign.getEndTime());



        Campaign newCampaign =  new Campaign();

        newCampaign.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        newCampaign.setName(campaign.getName());
        newCampaign.setContent(campaign.getContent());
        newCampaign.setType(campaign.getType());
        newCampaign.setServiceId(campaign.getServiceId());
        newCampaign.setTelcoId(campaign.getTelcoId());
        newCampaign.setRetry(campaign.getRetry());
        newCampaign.setStartTime(campaign.getStartTime());
        newCampaign.setEndTime(campaign.getEndTime());
        newCampaign.setVoice(campaign.getVoice());

        campaignReposirity.save(newCampaign);

        return newCampaign;
    }

    @Override
    public Campaign updateCampaign(Campaign campaign) {



        System.out.println(campaign.getContent());

        Optional<Campaign> newCampaign = campaignReposirity.findById(campaign.getId());

        if(newCampaign.isPresent()){
            newCampaign.get().setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            newCampaign.get().setName(campaign.getName());
            newCampaign.get().setContent(campaign.getContent());
            newCampaign.get().setType(campaign.getType());
            newCampaign.get().setServiceId(campaign.getServiceId());
            newCampaign.get().setTelcoId(campaign.getTelcoId());
            newCampaign.get().setRetry(campaign.getRetry());
            newCampaign.get().setStartTime(campaign.getStartTime());
            newCampaign.get().setEndTime(campaign.getEndTime());
            newCampaign.get().setVoice(campaign.getVoice());
//            if(!newCampaign.get().getFileName().equals(campaign.getFileName())){
//                try{
//                    if (newCampaign.get().getType() == 0){
//                        emailReposirity.deleteAll();
//                    }
//                    else if(newCampaign.get().getType() == 1){
//                        phoneReposirity.deleteAll();
//                    }
//                    importPhoneEmail(newCampaign.get().getId(), campaign.getFileName());
//                }finally {
//
//                }
//
//            }
            campaignReposirity.save(newCampaign.get());

            return newCampaign.get();
        }


        return null;
    }

    @Override
    public String importPhoneEmail(int idCampaign, String fileName) {

        Optional<Campaign> newCampaign = campaignReposirity.findById(idCampaign);
        newCampaign.get().setFileName(fileName);

        if(newCampaign.isPresent()){
            String line = "";
            String splitBy = ",";
            if(newCampaign.get().getType() == 0){

                try
                {

                    BufferedReader br = new BufferedReader(new FileReader("/Users/apple/Downloads/Tranning_Vega/SimpleProject/src/main/resources/static/" + fileName));
                    while ((line = br.readLine()) != null)   //returns a Boolean value
                    {
                        Email email = new Email();
                        email.setStatus(Integer.parseInt(line.split(splitBy)[0]));
                        email.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                        email.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
                        email.setEmail(line.split(splitBy)[1]);
                        email.setRepeat(Integer.parseInt(line.split(splitBy)[2]));
                        email.setServiceId(newCampaign.get().getServiceId());
                        email.setCampaignId(newCampaign.get().getId());
                        if(email.getStatus() == 0){newCampaign.get().setStatus(0);}
                        emailReposirity.save(email);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                campaignReposirity.save(newCampaign.get());
                return "Done";
            }

            else if(newCampaign.get().getType() == 1){
                try
                {

                    BufferedReader br = new BufferedReader(new FileReader("/Users/apple/Downloads/Tranning_Vega/SimpleProject/src/main/resources/static/" + fileName));
                    while ((line = br.readLine()) != null)   //returns a Boolean value
                    {
                        Phone phone = new Phone();
                        phone.setStatus(Integer.parseInt(line.split(splitBy)[0]));
                        phone.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                        phone.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
                        phone.setPhone(line.split(splitBy)[1]);
                        phone.setServiceId(newCampaign.get().getServiceId());
                        phone.setTelcoId(newCampaign.get().getTelcoId());
                        phone.setRetry(newCampaign.get().getRetry());
                        phone.setCampaignId(newCampaign.get().getId());
                        if(phone.getStatus() == 0){newCampaign.get().setStatus(0);}
                        phoneReposirity.save(phone);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                campaignReposirity.save(newCampaign.get());
                return "Done";
            }

        }

        return "Fail";
    }

    @Override
    public String delCampaign(int idCampaign) {
        if(campaignReposirity.findById(idCampaign).isPresent()){
            campaignReposirity.deleteById(idCampaign);
            return "Deleted Successfully";
        }
        return "Fail";
    }


}
