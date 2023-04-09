package com.example.SimpleProject.Repository;

import com.example.SimpleProject.Entities.Campaign;
import com.example.SimpleProject.Projection.CampaignEmailProjection;
import com.example.SimpleProject.Projection.CampaignPhoneProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampaignReposirity extends JpaRepository<Campaign, Integer> {
    @Query(value = "select campaign.created_time as createdTime, email_list.status, email_list.email, email_list.retry, email_list.service_id as serviceId from campaign inner join email_list ON email_list.campaign_id = ?1 and campaign.id = ?1 ", nativeQuery = true)
    List<CampaignEmailProjection> getEmailStatic(int idCampaign);

    @Query(value = "select campaign.created_time as createdTime, phone_list.status, phone_list.phone, phone_list.retry, phone_list.service_id as serviceId from campaign inner join phone_list ON phone_list.campaign_id = ?1 and campaign.id = ?1 ", nativeQuery = true)
    List<CampaignPhoneProjection> getPhoneStatic(int idCampaign);

    Boolean existsById(int id);

}
