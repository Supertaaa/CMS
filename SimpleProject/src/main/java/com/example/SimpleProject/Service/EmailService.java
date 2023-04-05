package com.example.SimpleProject.Service;


import com.example.SimpleProject.Entities.Email;

public interface EmailService {
    Email createEmail(Email email);
    Email updateEmail(Email email);
    String delEmail(int idEmail);
}
