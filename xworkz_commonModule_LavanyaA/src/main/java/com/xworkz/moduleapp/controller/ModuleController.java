package com.xworkz.moduleapp.controller;

import com.xworkz.moduleapp.dto.ModuleDto;
import com.xworkz.moduleapp.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    public ModuleController() {
        System.out.println("ModuleController object is created");
    }

    @PostMapping("/userSignUp")
    public String userSignUp(@Valid @ModelAttribute("moduleDto") ModuleDto moduleDto, Model model) {
        System.out.println("user sign up is running..");
        return moduleService.signUpValidateAndSave(moduleDto, model);
    }

    @PostMapping("/userSignIn")
    public String userSignIn(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        System.out.println("user sign in is running..");
        return moduleService.getSignIn(email, password, model);
    }
}
