package com.example.SimpleProject.Entities;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="phone_list")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone extends BaseEntity{
	@Column(name = "status")
	@ColumnDefault(value = "0")
	public int status;
	
	@Column(name="phone")
	@JsonInclude(Include.NON_EMPTY)
	public String phone;
	
	
	@Column(name = "service_id")
	@ColumnDefault("0")
	public int serviceId;
	
	@Column(name="telco_id")
	@ColumnDefault("0")
	public int telcoId;
	
	@Column(name = "retry")
	@ColumnDefault(value = "0")
	public int retry;
	
	@Column(name="campaign_id")
	@JsonInclude(Include.NON_EMPTY)
	public Integer campaignId;
}
