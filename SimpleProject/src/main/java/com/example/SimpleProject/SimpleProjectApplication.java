package com.example.SimpleProject;

import com.example.SimpleProject.Entities.Email;
import com.example.SimpleProject.Projection.DataProjection;
import com.example.SimpleProject.Repository.EmailReposirity;
import com.example.SimpleProject.Repository.PhoneReposirity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class SimpleProjectApplication{

	public static void main(String[] args) {
		SpringApplication.run(SimpleProjectApplication.class, args);
	}


}
