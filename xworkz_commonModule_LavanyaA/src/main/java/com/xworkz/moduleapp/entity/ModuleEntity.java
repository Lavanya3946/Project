package com.xworkz.moduleapp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "module_details")

public class ModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "confirmPassword")
    private String confirmPassword;
}
