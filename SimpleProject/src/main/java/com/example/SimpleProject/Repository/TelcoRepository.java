package com.example.SimpleProject.Repository;


import com.example.SimpleProject.Entities.Telco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelcoRepository extends JpaRepository<Telco, Integer> {
}
