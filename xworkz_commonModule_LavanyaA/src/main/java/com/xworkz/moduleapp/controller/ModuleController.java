package com.xworkz.moduleapp.controller;

import com.xworkz.moduleapp.dto.ModuleDto;
import com.xworkz.moduleapp.entity.ModuleEntity;
import com.xworkz.moduleapp.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    public ModuleController() {
        System.out.println("ModuleController object is created");
    }

    @PostMapping("/userSignUp")
    public String userSignUp(@ModelAttribute("moduleDto") ModuleDto moduleDto, Model model) {
        boolean isSaved = moduleService.signUpValidateAndSave(moduleDto);

        if (!isSaved) {
            model.addAttribute("inValid", "Invalid input fields. Please check again.");
            return "signUp.jsp";
        } else {
            model.addAttribute("name", moduleDto.getFullName());
            return "signResponse.jsp";
        }
    }

    @GetMapping("/userSignIn")
    public String userSignIn(@RequestParam("email") String email,
                             @RequestParam("password") String password,
                             Model model) {
        String loginStatus = moduleService.getSignInStatus(email, password);

        switch (loginStatus) {
            case "LOCKED":
                LocalDateTime unlockTime = moduleService.getUnlockTime(email);
                if (unlockTime != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    model.addAttribute("unlockTime", unlockTime.format(formatter));
                    model.addAttribute("msg", "Your account is locked due to multiple failed login attempts. Try again after.");
                }
                return "signin.jsp";

            case "USER_NOT_FOUND":
            case "INVALID_PASSWORD":
                model.addAttribute("inValidData", "Invalid email or password");
                return "signin.jsp";

            case "SUCCESS":
                model.addAttribute("validData", email);
                return "signInResponse.jsp";

            default:
                model.addAttribute("msg", "Unexpected error. Please try again.");
                return "signin.jsp";
        }
    }

    @GetMapping("/fetchUsers")
    public String fetchByEmail(@RequestParam("email") String email, Model model) {
        ModuleDto moduleDto = moduleService.getByEmail(email);
        model.addAttribute("user", moduleDto);
        return "updatePage.jsp";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("moduleDto") ModuleDto moduleDto, Model model) {
        boolean isUpdated = moduleService.updateUser(moduleDto);

        if (isUpdated) {
            model.addAttribute("emailId", moduleDto.getEmail());
            model.addAttribute("msg", "Updated successfully!");
            return "updateSuccess.jsp";
        } else {
            model.addAttribute("msg", "Data not updated!");
            return "updatePage.jsp";
        }
    }

    @PostMapping("/unlockAccount")
    public String unlockAccount(@RequestParam("email") String email, Model model) {
        boolean isUnlocked = moduleService.unlockUserAccount(email);

        if (isUnlocked) {
            model.addAttribute("msg", "Your account has been unlocked. You can now log in.");
            return "signin.jsp";
        } else {
            model.addAttribute("msg", "Unable to unlock account. Contact support.");
            return "accountLocked.jsp";
        }
    }
}
