package com.example.SimpleProject.Projection;

import java.sql.Timestamp;
import java.time.LocalDate;

public interface CampaignEmailProjection {
    LocalDate getCreatedTime();
    Integer getStatus();
    String getEmail();
    Integer getRetry();
    Integer getServiceId();
}
