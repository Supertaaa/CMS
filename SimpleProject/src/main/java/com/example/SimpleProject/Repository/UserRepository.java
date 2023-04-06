package com.example.SimpleProject.Repository;

import com.example.SimpleProject.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String username);
    Boolean existsUserByUserName(String username);


}




