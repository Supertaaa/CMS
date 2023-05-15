package com.example.SimpleProject.Projection;

import java.sql.Timestamp;
import java.time.LocalDate;

public interface CampaignPhoneProjection {
    LocalDate getCreatedTime();
    Integer getStatus();
    String getPhone();
    Integer getRetry();
    Integer getServiceId();
}
