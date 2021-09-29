package com.bridgelabz.bookstoreapp.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "KYC")
    private String kyc;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDateTime dateOfBirth;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL_ID")
    private String emailId;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "IS_VERIFIED")
    public Boolean isVerified;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "OTP")
    private String otp;

    @Column(name = "REGISTERED_DATE")
    @CreationTimestamp
    private LocalDateTime registeredDate;

    @Column(name = "UPDATED_DATE")
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Column(name = "PURCHASED_DATE")
    private LocalDateTime purchasedDate;

    @Column(name = "EXPIRED_DATE")
    private LocalDateTime expiredDate;


}
