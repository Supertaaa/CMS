package com.example.SimpleProject.Projection;

import java.sql.Timestamp;

public interface CampaignPhoneProjection {
    Timestamp getCreatedTime();
    Integer getStatus();
    String getPhone();
    Integer getRetry();
    Integer getServiceId();
}
