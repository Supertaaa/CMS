package com.example.SimpleProject.Entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="created_time")
	@CreatedDate
	private LocalDate createdDate;
	
	@Column(name="updated_time")
	@LastModifiedDate
	private LocalDate updatedDate;
	
//	public LocalDate getCreatedDate() {
//		if(createdDate == null) {
//			return null;
//		}
//		return new  LocalDate(createdDate.);
//	}

//	public void setCreatedDate(Timestamp createdDate) {
//		this.createdDate = (createdDate == null ? null :new Timestamp(createdDate.getTime()));
//	}

//	public Timestamp getUpdatedDate() {
//		if(updatedDate ==null) {
//			return null;
//		}
//		return new Timestamp(updatedDate.getTime());
//	}

//	public void setUpdatedDate(Timestamp updatedDate) {
//		this.updatedDate = (updatedDate == null ? null : new Timestamp(updatedDate.getTime()));
//	}
}
