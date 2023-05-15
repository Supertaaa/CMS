package com.example.SimpleProject.Repository;


import com.example.SimpleProject.Entities.Phone;
import com.example.SimpleProject.Projection.DataProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneReposirity extends JpaRepository<Phone,Integer> {

    List<Phone> findPhoneByCampaignId(int campaignId);
    @Modifying
    @Query("delete from Phone b where b.campaignId=?1")
    void deletePhones(int id);
    @Query(value = "select c.id as campaignId, p.id as phoneId from campaign c inner join phone_list p on c.id = ?1 and  p.campaign_id = ?1  where c.status not in(1,2) and p.status = 0", nativeQuery = true)
    List<DataProjection> listNotStartedPhone(int idCampaign);
}
