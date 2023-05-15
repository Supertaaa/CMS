package com.example.SimpleProject.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.SimpleProject.Entities.BaseEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDTO extends BaseEntity {


    public String name;


    public String content;

    public String status;


    public String type;


    public String serviceId;


    public String telcoId;


    public int retry;


    private String fileName;



    private LocalDateTime startTime;


    private LocalDateTime endTime;


    private int voice=0;

}
