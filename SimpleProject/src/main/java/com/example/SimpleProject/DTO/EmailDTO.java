package com.example.SimpleProject.DTO;

import com.example.SimpleProject.Entities.BaseEntity;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO extends BaseEntity {


    private String status;

    private String email;

    private int repeat;

    private String serviceId;

    private String campaignId;


}
