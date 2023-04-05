package com.example.SimpleProject.Projection;

import java.sql.Timestamp;
public interface CampaignEmailProjection {
    Timestamp getCreatedTime();
    Integer getStatus();
    String getEmail();
    Integer getRetry();
    Integer getServiceId();
}
