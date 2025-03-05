package com.xworkz.moduleapp.dto;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ModuleDto {

    private Integer id;
    private String fullName;
    private String email;
   private Integer age;
    private String gender;
    private String location;
//    private LocalDate dob;
    private String phoneNumber;
    private String password;
    private String confirmPassword;


}
