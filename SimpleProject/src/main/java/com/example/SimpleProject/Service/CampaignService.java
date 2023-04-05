package com.example.SimpleProject.Service;


import com.example.SimpleProject.Entities.Campaign;

public interface CampaignService {
    Campaign createCampaign(Campaign campaign);
    Campaign updateCampaign(Campaign campaign);
    String importPhoneEmail(int idCampaign, String fileName);
    String delCampaign(int idCampaign);



}
