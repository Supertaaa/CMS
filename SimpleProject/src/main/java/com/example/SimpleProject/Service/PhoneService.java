package com.example.SimpleProject.Service;


import com.example.SimpleProject.Entities.Phone;

public interface PhoneService {
    Phone createPhone(Phone phone);
    Phone updatePhoone(Phone phone);
    String delPhone(int idPhone);
}
