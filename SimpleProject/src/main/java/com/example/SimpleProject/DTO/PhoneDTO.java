package com.example.SimpleProject.DTO;

import com.example.SimpleProject.Entities.BaseEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO extends BaseEntity {

    public String status;


    public String phone;


    public String serviceId;

    public String telcoId;

    public int retry;

    public String campaignId;
}
