package com.example.SimpleProject.Repository;


import com.example.SimpleProject.Entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailReposirity extends JpaRepository<Email, Integer> {
}
