package com.xworkz.moduleapp.controller;

import com.xworkz.moduleapp.dto.ModuleDto;
import com.xworkz.moduleapp.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Component
@RequestMapping("/")
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    public ModuleController() {
        System.out.println("ModuleController object is created");
    }

    @RequestMapping("/userSignUp")
    public String userSignUp(@Valid @ModelAttribute("moduleDto") ModuleDto moduleDto, Model model) {

        return moduleService.signUpValidateAndSave(moduleDto, model);
//        model.addAttribute("name",moduleDto);
//        return "signResponse.jsp";
    }
}

