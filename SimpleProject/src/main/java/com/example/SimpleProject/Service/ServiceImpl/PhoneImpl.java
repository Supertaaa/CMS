package com.example.SimpleProject.Service.ServiceImpl;



import com.example.SimpleProject.Entities.Campaign;
import com.example.SimpleProject.Entities.Phone;
import com.example.SimpleProject.Repository.CampaignReposirity;
import com.example.SimpleProject.Repository.PhoneReposirity;
import com.example.SimpleProject.Service.PhoneService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Transactional
public class PhoneImpl implements PhoneService {

    @Autowired
    PhoneReposirity phoneReposirity;

    @Autowired
    CampaignReposirity campaignReposirity;

    @Override
    public Phone createPhone(Phone phone) {
        Phone newPhone = new Phone();
        System.out.println(phone.getCampaignId());
        Optional<Campaign> campaign = campaignReposirity.findById(phone.getCampaignId());
        if (campaign.isPresent()){
            newPhone.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            newPhone.setStatus(phone.getStatus());
            newPhone.setPhone(phone.getPhone());
            newPhone.setServiceId(campaign.get().getServiceId());
            newPhone.setTelcoId(campaign.get().getTelcoId());
            newPhone.setRetry(campaign.get().getRetry());
            newPhone.setCampaignId(campaign.get().getId());


            phoneReposirity.save(newPhone);
            return newPhone;
        }
        return null;
    }

    @Override
    public Phone updatePhoone(Phone phone) {

        Optional<Phone> newPhone = phoneReposirity.findById(phone.getId());

        if(newPhone.isPresent()){
            newPhone.get().setCreatedDate(new Timestamp(System.currentTimeMillis()));
            newPhone.get().setStatus(phone.getStatus());
            newPhone.get().setPhone(phone.getPhone());
            newPhone.get().setServiceId(phone.getServiceId());
            newPhone.get().setTelcoId(phone.getTelcoId());
            newPhone.get().setRetry(phone.getRetry());
            newPhone.get().setCampaignId(phone.getCampaignId());

            phoneReposirity.save(newPhone.get());
            return newPhone.get();
        }

        return null;
    }

    @Override
    public String delPhone(int idPhone) {

        if(phoneReposirity.findById(idPhone).isPresent()){
            phoneReposirity.deleteById(idPhone);
            return "Done";
        }

        return "Fail";
    }
}
