package com.example.SimpleProject.Repository;


import com.example.SimpleProject.Entities.Email;
import com.example.SimpleProject.Projection.DataProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmailReposirity extends JpaRepository<Email, Integer> {
    List<Email> findEmailByCampaignId(int campaignId);
    @Modifying
    @Query("delete from Email b where b.campaignId=?1")
    void deleteEmails(int id);


    @Query(value = "select c.id as campaignId, e.id as emailId from campaign c inner join email_list e on c.id = ?1 and e.campaign_id = ?1 where c.status not in(1,2) and e.status = 0", nativeQuery = true)
    List<DataProjection> listNotStartedEmail(int idCampaign);

}
