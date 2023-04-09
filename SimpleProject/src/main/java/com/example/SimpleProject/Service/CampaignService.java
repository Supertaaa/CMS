package com.example.SimpleProject.Service;


import com.example.SimpleProject.Entities.Campaign;
import org.springframework.web.multipart.MultipartFile;

public interface CampaignService {
    Campaign createCampaign(Campaign campaign);
    Campaign updateCampaign(Campaign campaign);
    String importPhoneEmail(String idCampaign, MultipartFile fileName);
    String delCampaign(int idCampaign);



}
