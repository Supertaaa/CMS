package com.example.SimpleProject.Repository;


import com.example.SimpleProject.Entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneReposirity extends JpaRepository<Phone,Integer> {
}
