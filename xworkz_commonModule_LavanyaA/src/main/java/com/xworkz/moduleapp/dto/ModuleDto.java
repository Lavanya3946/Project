package com.xworkz.moduleapp.dto;

import lombok.*;

import javax.inject.Named;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ModuleDto {
    private Integer id;

    @NotBlank(message = "Full Name is required!")
    @Pattern(regexp = "^[A-Z][a-zA-Z ]*$", message = "Name should start with an uppercase letter and contain only letters")
    @Size(min = 3, max = 20, message = "Name should be between 3 and 20 characters")
    private String fullName;


    private String email;


    @NotBlank(message = "phone Number cannot be blank")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "phone number must be 10 digits and it should starts with 6,7,8,or 9")
    private String phoneNumber;


    @NotBlank(message = "password can not be blank")
    @Size(min = 6, message = "password has to  min 6 characters")
    private String password;


    @NotBlank(message = "confirm password can not be blank")
    private String confirmPassword;

}
