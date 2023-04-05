package com.example.SimpleProject.Entities;
import javax.persistence.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="campaign")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campaign extends BaseEntity{
	
	@Column(name="name")
	@JsonInclude(Include.NON_EMPTY)
	public String name;
	
	@Column(name="content")
	@JsonInclude(Include.NON_EMPTY)
	public String content;
	
	@Column(name = "`status`")
	@ColumnDefault("0")
	public int status = 0;

	@Column(name = "type")
	public int type;
	
	@Column(name = "service_id")
	@ColumnDefault("0")
	public int serviceId;
	
	@Column(name="telco_id")
	@ColumnDefault("0")
	public int telcoId;
	
	@Column(name="retry")
	@ColumnDefault("0")
	public int retry;

	@Column(name="file_name")
	private String fileName;
	
	
	@Column(name = "start_time")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	//@DateTimeFormat(iso = DateTimeFormatter.ISO_LOCAL_DATE_TIME)
	private LocalDateTime startTime;
	
	@Column(name = "end_time")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	//@DateTimeFormat(iso = DateTimeFormatter.ISO_LOCAL_DATE_TIME)
	private LocalDateTime endTime;
	
	@Column(name="`voice`")
	@ColumnDefault("0")
	private int voice=0;

}
