package com.example.SimpleProject.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Service extends BaseEntity {

    private String name;
    private String description;
    private int deleted;

    private String createdBy;
    private String updatedBy;

    @ColumnDefault("1")
    private int status;

}
