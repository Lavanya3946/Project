package com.xworkz.moduleapp.service;

import com.xworkz.moduleapp.dto.ModuleDto;
import com.xworkz.moduleapp.entity.ModuleEntity;
import com.xworkz.moduleapp.repo.ModuleRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ModuleServiceImpl implements ModuleService {

    private static final int MAX_FAILED_ATTEMPTS = 3; // Maximum failed attempts before locking account

    @Autowired
    private ModuleRepo moduleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean signUpValidateAndSave(ModuleDto moduleDto) {
        if (!isValidUser(moduleDto)) {
            return false;
        }

        // Encrypt the password before saving
        moduleDto.setPassword(passwordEncoder.encode(moduleDto.getPassword()));
        moduleDto.setConfirmPassword(moduleDto.getPassword());

        // Convert DTO to Entity and save
        ModuleEntity moduleEntity = new ModuleEntity();
        BeanUtils.copyProperties(moduleDto, moduleEntity);
        moduleRepo.signUpSave(moduleEntity);

        return true;
    }

    private boolean isValidUser(ModuleDto moduleDto) {
        String fullName = moduleDto.getFullName();
        String phoneNumber = moduleDto.getPhoneNumber();
        String email = moduleDto.getEmail();
        String password = moduleDto.getPassword();
        Integer age = moduleDto.getAge();
        String gender = moduleDto.getGender();
        String location = moduleDto.getLocation();

        // Full Name validation
        if (fullName == null || !fullName.matches("^[A-Z][a-z]+( [A-Z][a-z]*)?$") || fullName.length() < 3 || fullName.length() > 20) {
            return false;
        }

        // Email validation
        if (email == null || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") || isEmailExists(email)) {
            return false;
        }

        // Age validation
        if (age == null || age < 10) {
            return false;
        }

        // Gender and location validation
        if (gender == null || location == null) {
            return false;
        }

        // Phone Number validation
        if (phoneNumber == null || !phoneNumber.matches("^[789]\\d{9}$")) {
            return false;
        }

        // Password validation
        if (password == null || password.length() < 8 || password.length() > 20 ||
                !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") ||
                !password.matches(".*\\d.*") || !password.matches(".*[@#$%^&*?=+].*")) {
            return false;
        }

        // Password confirmation
        return moduleDto.getConfirmPassword() != null && password.equals(moduleDto.getConfirmPassword());
    }


    @Override
    public ModuleDto getByEmail(String email) {
        ModuleEntity moduleEntity = moduleRepo.findByEmail(email);
        if (moduleEntity == null) {
            System.out.println("No user found for email: " + email);
            return null;
        }

        ModuleDto moduleDto = new ModuleDto();
        BeanUtils.copyProperties(moduleEntity, moduleDto);
        return moduleDto;
    }

    @Override
    public boolean updateUser(ModuleDto userDto) {
        if (userDto == null) {
            return false;
        }

        ModuleEntity userEntity = new ModuleEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        return moduleRepo.updateUser(userEntity);
    }

    @Override
    public void increaseFailedAttempts(String email) {
        ModuleEntity moduleEntity = moduleRepo.findByEmail(email);
        if (moduleEntity != null) {
            int newFailedAttempts = moduleEntity.getFailedAttempts() + 1;
            moduleEntity.setFailedAttempts(newFailedAttempts);

            if (newFailedAttempts >= MAX_FAILED_ATTEMPTS) {
                lockUserAccount(email);
            } else {
                moduleRepo.updateUser(moduleEntity);
            }
        }
    }

    @Override
    public void lockUserAccount(String email) {
        ModuleEntity moduleEntity = moduleRepo.findByEmail(email);
        if (moduleEntity != null) {
            moduleEntity.setAccountLocked(true);
            moduleEntity.setLockTime(LocalDateTime.now());  // Store lock time
            moduleRepo.updateUser(moduleEntity);
            System.out.println("User account locked at: " + moduleEntity.getLockTime());
        }
    }

    @Override
    public void resetFailedAttempts(String email) {
        ModuleEntity moduleEntity = moduleRepo.findByEmail(email);
        if (moduleEntity != null) {
            moduleEntity.setFailedAttempts(0);
            moduleEntity.setAccountLocked(false);
            moduleEntity.setLockTime(null);  // Reset lock time
            moduleRepo.updateUser(moduleEntity);
        }
    }

    @Override
    public String getSignInStatus(String email, String password) {
        ModuleEntity moduleEntity = moduleRepo.findByEmail(email);

        if (moduleEntity == null) {
            return "USER_NOT_FOUND";
        }

        // Check if the account is locked
        if (moduleEntity.isAccountLocked()) {
            LocalDateTime unlockTime = moduleEntity.getLockTime().plusHours(24);

            if (LocalDateTime.now().isAfter(unlockTime)) {
                // Unlock account automatically
                unlockUserAccount(email);
                System.out.println("Account unlocked automatically for: " + email);
            } else {
                return "LOCKED";
            }
        }

        // Verify password
        if (!passwordEncoder.matches(password, moduleEntity.getPassword())) {
            increaseFailedAttempts(email);
            return "INVALID_PASSWORD";
        }

        // Reset failed attempts on successful login
        resetFailedAttempts(email);
        return "SUCCESS";
    }


    @Override
    public boolean unlockUserAccount(String email) {
        ModuleEntity moduleEntity = moduleRepo.findByEmail(email);

        if (moduleEntity != null && moduleEntity.isAccountLocked()) {
            moduleEntity.setAccountLocked(false);
            moduleEntity.setFailedAttempts(0);
            moduleEntity.setLockTime(null);  // Reset lock time
            moduleRepo.updateUser(moduleEntity);
            System.out.println("User account unlocked successfully for: " + email);
            return true;
        }

        return false; // User not found or already unlocked
    }

    @Override
    public boolean isEmailExists(String email) {
        return moduleRepo.findByEmail(email) != null;
    }

    @Override
    public LocalDateTime getUnlockTime(String email) {
        ModuleEntity moduleEntity = moduleRepo.findByEmail(email);

        if (moduleEntity != null && moduleEntity.isAccountLocked() && moduleEntity.getLockTime() != null) {
            return moduleEntity.getLockTime().plusHours(24); // Unlock after 24 hours
        }
        return null;
    }
}
