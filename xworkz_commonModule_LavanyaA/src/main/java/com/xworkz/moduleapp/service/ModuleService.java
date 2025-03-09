package com.xworkz.moduleapp.service;

import com.xworkz.moduleapp.dto.ModuleDto;
import com.xworkz.moduleapp.entity.ModuleEntity;
import org.springframework.ui.Model;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public interface ModuleService {

    boolean signUpValidateAndSave(ModuleDto moduleDto);



    ModuleDto getByEmail(String email);

    boolean updateUser(ModuleDto userDto);

    void increaseFailedAttempts(String email);  // New method

    void lockUserAccount(String email);  // New method

    void resetFailedAttempts(String email);

     String getSignInStatus(String email, String password);

    boolean unlockUserAccount(String email);

    boolean isEmailExists(String email);

    LocalDateTime getUnlockTime(String email);




}
