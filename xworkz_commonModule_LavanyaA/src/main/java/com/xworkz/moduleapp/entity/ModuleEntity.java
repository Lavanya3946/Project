package com.xworkz.moduleapp.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
@NamedQuery(name = "getByEmail",query = "select m from ModuleEntity m where m.email=:email")


public class ModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "location")
    private String location;

//    @Column(name = "dob")
//    private LocalDate dob;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "failed_attempts")
    private int failedAttempts = 0;  // Track failed login attempts

    @Column(name = "account_locked")
    private boolean accountLocked = false;

    @Column(name = "lock_time")
    private LocalDateTime lockTime;


}
