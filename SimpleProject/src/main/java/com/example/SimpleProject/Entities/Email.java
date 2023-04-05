package com.example.SimpleProject.Entities;
import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="email_list")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email extends BaseEntity{
	
	
	@Column(name = "STATUS")
	@ColumnDefault(value = "0")
	private int status;
	
	@Column(name="email")
	@JsonInclude(Include.NON_EMPTY)
	private String email;
	
	@Column(name = "retry")
	@ColumnDefault(value = "0")
	private int repeat;
	
	@Column(name = "service_id")
	@ColumnDefault("0")
	private int serviceId;
	
	
	@Column(name="campaign_id")
	@JsonInclude(Include.NON_EMPTY)
	private Integer campaignId;
	
	
}
