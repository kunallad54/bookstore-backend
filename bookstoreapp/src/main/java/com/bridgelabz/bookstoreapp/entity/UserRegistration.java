package com.bridgelabz.bookstoreapp.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "user_registration")
public class UserRegistration{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    private String firstName;
    private String lastName;
    private String kyc;

    private LocalDate dateOfBirth;

    private String password;
    private String emailId;
    private String phoneNumber;

    public Boolean isVerified;
    private String userType;

    private String otp;

    @CreationTimestamp
    private LocalDateTime registeredDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;


}
