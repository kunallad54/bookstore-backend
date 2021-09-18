package com.bridgelabz.bookstoreapp.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public class BaseModel {

    @CreationTimestamp
    private LocalDateTime registeredDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    private String otp;
}
