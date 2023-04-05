package com.example.SimpleProject.Entities;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="configs")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Configs extends BaseEntity{
	@Column(name="config_name")
	private String configName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="group_name")
	private String groupName;
	
	@Column(name="val")
	private String val;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	@Column(name="created_by")
	private Integer createdBy;
}
