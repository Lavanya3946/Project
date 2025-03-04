package com.xworkz.moduleapp.service;

import com.xworkz.moduleapp.dto.ModuleDto;
import org.springframework.ui.Model;

public interface ModuleService {

    String signUpValidateAndSave(ModuleDto moduleDto, Model model);
}
