package com.example.SimpleProject.Entities;
import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="sent_log")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentLog extends BaseEntity{
	
	@Column(name = "customer")
	private String customer;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "status")
	private int status;
	
	
	@Column(name = "service_id")
	@ColumnDefault("0")
	private Integer serviceId;
	
	@Column(name="telco_id")
	@ColumnDefault("0")
	private Integer telcoId;
	
	@Column(name="campain_id")
	private Integer campainId;
	
	@Column(name = "return_data")
	private String returnLog;
	
	@Column(name = "type")
	private Integer type;
}
