package com.example.SimpleProject.Service.ServiceImpl;


import com.example.SimpleProject.Entities.Campaign;
import com.example.SimpleProject.Entities.Email;
import com.example.SimpleProject.Repository.CampaignReposirity;
import com.example.SimpleProject.Repository.EmailReposirity;
import com.example.SimpleProject.Service.EmailService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;


@Service
@Transactional
public class EmailImpl implements EmailService {

    @Autowired
    EmailReposirity emailReposirity;

    @Autowired
    CampaignReposirity campaignReposirity;
    @Override
    public Email createEmail(Email email) {

        System.out.print(email.getEmail());
        Optional<Campaign> campaign = campaignReposirity.findById(email.getCampaignId());
        Email newEmail = new Email();

        if(campaign.isPresent()){
            if(campaign.get().getType() != 0){return null;}

            newEmail.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            newEmail.setStatus(email.getStatus());
            newEmail.setEmail(email.getEmail());
            newEmail.setRepeat(email.getRepeat());
            newEmail.setServiceId(campaign.get().getServiceId());
            newEmail.setCampaignId(email.getCampaignId());

            emailReposirity.save(newEmail);
            return newEmail;
        }
        return null;

    }

    @Override
    public Email updateEmail(Email email) {

        Optional<Email> newEmail = emailReposirity.findById(email.getId());

        Optional<Campaign> campaign = campaignReposirity.findById(email.getCampaignId());

        if (newEmail.isPresent()){
            if (campaign.isPresent()){
                if(campaign.get().getType() != 0){return null;}

                newEmail.get().setUpdatedDate(new Timestamp(System.currentTimeMillis()));
                newEmail.get().setStatus(email.getStatus());
                newEmail.get().setEmail(email.getEmail());
                newEmail.get().setRepeat(email.getRepeat());
                newEmail.get().setServiceId(campaign.get().getServiceId());
                newEmail.get().setCampaignId(email.getCampaignId());

                emailReposirity.save(newEmail.get());

                return newEmail.get();
            }
        }

        return null;

    }

    @Override
    public String delEmail(int idEmail) {

        if(emailReposirity.findById(idEmail).isPresent()){
            emailReposirity.deleteById(idEmail);

            return "Done";
        }
        return "Fail";
    }
}
