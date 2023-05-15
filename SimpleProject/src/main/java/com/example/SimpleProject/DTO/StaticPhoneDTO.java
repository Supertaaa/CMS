package com.example.SimpleProject.DTO;

import com.example.SimpleProject.Entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaticPhoneDTO extends BaseEntity {

    private LocalDate createdDate;

    private String phone;

    private Integer retry;

    private String service;

    private String status;

}
