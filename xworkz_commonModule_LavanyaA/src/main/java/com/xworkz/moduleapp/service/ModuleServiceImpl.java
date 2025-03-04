package com.xworkz.moduleapp.service;

import com.xworkz.moduleapp.dto.ModuleDto;
import com.xworkz.moduleapp.entity.ModuleEntity;
import com.xworkz.moduleapp.repo.ModuleRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepo moduleRepo;

    @Override
    public String signUpValidateAndSave(ModuleDto moduleDto, Model model) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<ModuleDto>> violations = validator.validate(moduleDto);

        for (ConstraintViolation<ModuleDto> violation : violations) {

            if ("fullName".equals(violation.getPropertyPath().toString())) {
                model.addAttribute("nameError", violation.getMessage());
            }
            if ("phoneNumber".equals(violation.getPropertyPath().toString())) {
                model.addAttribute("phoneError", violation.getMessage());
            }
            if ("password".equals(violation.getPropertyPath().toString())) {
                model.addAttribute("passwordError", violation.getMessage());
            }
        }

        if (!moduleDto.getPassword().equals(moduleDto.getConfirmPassword())) {
            model.addAttribute("confirmPasswordError", "password does not match");
        }


        if (!violations.isEmpty() || model.containsAttribute("confirmPasswordError")) {
            model.addAttribute("moduleDto", moduleDto);  // Keep form data
            return "signUp.jsp";
        }


        ModuleEntity moduleEntity = new ModuleEntity();
        BeanUtils.copyProperties(moduleDto, moduleEntity);
        moduleRepo.signUpSave(moduleEntity);

        model.addAttribute("successMessage", "User signed up successfully!");
        return "signUp.jsp";
    }
}






