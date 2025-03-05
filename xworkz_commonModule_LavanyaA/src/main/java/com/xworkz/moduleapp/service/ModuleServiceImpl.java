package com.xworkz.moduleapp.service;

import com.xworkz.moduleapp.dto.ModuleDto;
import com.xworkz.moduleapp.entity.ModuleEntity;
import com.xworkz.moduleapp.repo.ModuleRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepo moduleRepo;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String signUpValidateAndSave(ModuleDto moduleDto, Model model) {
        String fullName = moduleDto.getFullName();
        String phoneNumber = moduleDto.getPhoneNumber();
        String email = moduleDto.getEmail();
        String password = moduleDto.getPassword();
        Integer age = moduleDto.getAge();
        String gender = moduleDto.getGender();
        String location = moduleDto.getLocation();

        // Validation for fullName
        if (fullName == null || fullName.isEmpty()) {
            model.addAttribute("nameError", " Name is required");
            return "signUp.jsp";
        }
        if (!fullName.matches("^[A-Z].*")) {
            model.addAttribute("nameError", "Name must start with an uppercase letter");
            return "signUp.jsp";
        }
        if (!fullName.matches("^[A-Z][a-z]+( [A-Z][a-z]*)?$")) {
            model.addAttribute("nameError", "Invalid name format. ");
            return "signUp.jsp";
        }
        if (fullName.length() < 3 || fullName.length() > 20) {
            model.addAttribute("nameError", " Name must be between 3 and 20 characters");
            return "signUp.jsp";
        }

        // Validation for email
        if (email == null || email.isEmpty()) {
            model.addAttribute("emailError", "Email is required");
            return "signUp.jsp";
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            model.addAttribute("emailError", "Invalid email format (e.g., user@example.com)");
            return "signUp.jsp";
        }
        // Check if email is already registered
        if (isEmailExists(email)) {
            model.addAttribute("emailError", "Email already registered. Please log in.");
            return "signUp.jsp";
        }

        // Validation for age
        if (age == null || age < 10) {
            model.addAttribute("ageError", "Age should be above 10");
            return "signUp.jsp";
        }

        // Validation for gender
        if (gender == null) {
            model.addAttribute("genderError", "Gender is required");
            return "signUp.jsp";
        }

        // Validation for location
        if (location == null) {
            model.addAttribute("locationError", "Location cannot be empty");
            return "signUp.jsp";
        }

        // Validation for phoneNumber
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            model.addAttribute("phoneNumberError", "Phone number is required");
            return "signUp.jsp";
        }
        if (!phoneNumber.matches("^\\d{10}$")) {
            model.addAttribute("phoneNumberError", "Phone number must be exactly 10 digits");
            return "signUp.jsp";
        }
        if (!phoneNumber.matches("^[789]\\d{9}$")) {
            model.addAttribute("phoneNumberError", "Phone number must start with 7, 8, or 9");
            return "signUp.jsp";
        }

        // Validation for password
        if (password == null || password.isEmpty()) {
            model.addAttribute("passwordError", "Password is required");
            return "signUp.jsp";
        }
        if (password.length() < 8 || password.length() > 20) {
            model.addAttribute("passwordError", "Password must be between 8 and 20 characters long");
            return "signUp.jsp";
        }
        if (!password.matches(".*[A-Z].*")) {
            model.addAttribute("passwordError", "Password must contain at least one uppercase letter");
            return "signUp.jsp";
        }
        if (!password.matches(".*[a-z].*")) {
            model.addAttribute("passwordError", "Password must contain at least one lowercase letter");
            return "signUp.jsp";
        }
        if (!password.matches(".*\\d.*")) {
            model.addAttribute("passwordError", "Password must contain at least one number");
            return "signUp.jsp";
        }
        if (!password.matches(".*[@#$%^&*?=+].*")) { // **Fixed special character regex**
            model.addAttribute("passwordError", "Password must contain at least one special character");
            return "signUp.jsp";
        }

        // Validation for password and confirm password
        if (moduleDto.getConfirmPassword() == null || moduleDto.getConfirmPassword().isEmpty()) {
            model.addAttribute("confirmPasswordError", "Confirm password is required");
            return "signUp.jsp";
        }
        if (!moduleDto.getPassword().equals(moduleDto.getConfirmPassword())) {
            model.addAttribute("confirmPasswordError", "Password does not match");
            return "signUp.jsp";
        }
        String encodedPassword=passwordEncoder.encode(moduleDto.getPassword());
        moduleDto.setPassword(encodedPassword);
        moduleDto.setConfirmPassword(encodedPassword);

        // Saving user data
        ModuleEntity moduleEntity = new ModuleEntity();
        BeanUtils.copyProperties(moduleDto, moduleEntity);
        moduleRepo.signUpSave(moduleEntity);

        model.addAttribute("name", fullName);
        return "signResponse.jsp";
    }

    @Override
    public String getSignIn(String email, String password, Model model) {
        ModuleEntity moduleEntity = moduleRepo.findByEmail(email);

        // Check if email exists
        if (moduleEntity == null) {
            model.addAttribute("emailError", "Email does not exist");
            return "signin.jsp";
        }

        if(!passwordEncoder.matches(password,moduleEntity.getPassword())){
            model.addAttribute("error","Invalid password");
            return "signin.jsp";
        }

        // **Check if password is correct**
//        if (!moduleEntity.getPassword().equals(password)) {
//            model.addAttribute("error", "Invalid password");
//            return "signin.jsp";
//        }
        if(moduleEntity.getPassword()==null||moduleEntity.getPassword().isEmpty()){
            model.addAttribute("error","password is required");
            return "signin.jsp";
        }

        // **Sign-in successful**
        model.addAttribute("name", moduleEntity.getFullName());
        return "signInResponse.jsp";
    }

    //  method to check if email exists
    public boolean isEmailExists(String email) {
        return moduleRepo.findByEmail(email) != null;
    }
}
