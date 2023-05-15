package com.example.SimpleProject.Service.ServiceImpl;

import com.example.SimpleProject.Entities.Campaign;
import com.example.SimpleProject.Entities.Email;
import com.example.SimpleProject.Entities.Phone;
import com.example.SimpleProject.Repository.*;


import com.example.SimpleProject.Service.CampaignService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import java.time.format.DateTimeFormatter;
import java.util.List;
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
        System.out.println(campaign);

        System.out.println(campaign.getName());

        System.out.println(campaign.getStartTime());
        System.out.println(campaign.getEndTime());



        Campaign newCampaign =  new Campaign();

        //newCampaign.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        newCampaign.setCreatedDate(java.time.LocalDate.now());
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


        Optional<Campaign> newCampaign = campaignReposirity.findById(campaign.getId());

//        newCampaign.get().setName(campaign.getName());
//        newCampaign.get().setContent(campaign.getContent());
//        newCampaign.get().setType(campaign.getType());
//        newCampaign.get().setServiceId(123);
//        newCampaign.get().setTelcoId(campaign.getTelcoId());
//        newCampaign.get().setRetry(campaign.getRetry());
//        newCampaign.get().setStartTime(campaign.getStartTime());
//        newCampaign.get().setEndTime(campaign.getEndTime());
//        newCampaign.get().setVoice(campaign.getVoice());

        System.out.println(campaign.getServiceId());
        if(newCampaign.isPresent()){
            newCampaign.get().setUpdatedDate(java.time.LocalDate.now());
            if(!campaign.getName().equals("")){
                newCampaign.get().setName(campaign.getName());
            }
            if(!campaign.getContent().equals("")){
                newCampaign.get().setContent(campaign.getContent());
            }

            newCampaign.get().setType(campaign.getType());

            if(campaign.getType() == 0){
                List<Email> listEmail = emailReposirity.findEmailByCampaignId(campaign.getId());
                for(Email email: listEmail){
                    email.setServiceId(campaign.getServiceId());
                    emailReposirity.save(email);
                }
            }
            else if(campaign.getType() == 1){
                List<Phone> listPhone = phoneReposirity.findPhoneByCampaignId(campaign.getId());
                for(Phone phone: listPhone){
                    phone.setTelcoId(campaign.getTelcoId());
                    phone.setServiceId(campaign.getServiceId());
                    phoneReposirity.save(phone);
                }
            }



            if(campaign.getServiceId() != 0){
                newCampaign.get().setServiceId(campaign.getServiceId());
            }
            if(campaign.getTelcoId() != 0){
                newCampaign.get().setTelcoId(campaign.getTelcoId());
            }
//            if(campaign.getRetry() != 0){
//                newCampaign.get().setRetry(campaign.getRetry());
//            }
            if(campaign.getStartTime() != null){
                newCampaign.get().setStartTime(campaign.getStartTime());
            }
            if(campaign.getEndTime() != null){
                newCampaign.get().setEndTime(campaign.getEndTime());
            }
            if(campaign.getVoice() != 0){
                newCampaign.get().setVoice(campaign.getVoice());
            }




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
//
            return newCampaign.get();
        }


        return null;
    }

    @Override
    public String importPhoneEmail(String idCampaign, MultipartFile fileName) {


        Optional<Campaign> newCampaign = campaignReposirity.findById(Integer.parseInt(idCampaign));

        if(newCampaign.isPresent()){
            newCampaign.get().setFileName(fileName.getName());
            String line = "";
            String splitBy = ",";
            if(newCampaign.get().getType() == 0){

                try
                {
                    InputStream inputStream = fileName.getInputStream();

                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    while ((line = br.readLine()) != null)   //returns a Boolean value
                    {

                        Email email = new Email();


                        email.setCreatedDate(java.time.LocalDate.now());
                        email.setUpdatedDate(java.time.LocalDate.now());

                        email.setEmail(line.split(splitBy)[0]);

                        email.setServiceId(newCampaign.get().getServiceId());
                        email.setCampaignId(newCampaign.get().getId());


                        emailReposirity.save(email);
                    }
                    campaignReposirity.save(newCampaign.get());
                    return "Done";
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }


            }

            else if(newCampaign.get().getType() == 1){
                try
                {

                    InputStream inputStream = fileName.getInputStream();

                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    while ((line = br.readLine()) != null)   //returns a Boolean value
                    {

                        Phone phone = new Phone();


                        phone.setCreatedDate(java.time.LocalDate.now());
                        phone.setUpdatedDate(java.time.LocalDate.now());

                        phone.setPhone(line.split(splitBy)[0]);

                        phone.setServiceId(newCampaign.get().getServiceId());
                        phone.setTelcoId(newCampaign.get().getTelcoId());

                        phone.setCampaignId(newCampaign.get().getId());


                        phoneReposirity.save(phone);
                    }
                    campaignReposirity.save(newCampaign.get());
                    return "Done";

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }


            }

        }


        return "Fail";
    }

    @Override
    public String delCampaign(int idCampaign) {
        if(campaignReposirity.findById(idCampaign).isPresent()){
            if(campaignReposirity.findById(idCampaign).get().getType() == 0){
                emailReposirity.deleteEmails(idCampaign);
            }
            else{
                phoneReposirity.deletePhones(idCampaign);
            }
            campaignReposirity.deleteById(idCampaign);



            return "Deleted Successfully";
        }
        return "Fail";
    }


}
