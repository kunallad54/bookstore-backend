package com.bridgelabz.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyUserDTO {

    @NotNull(message = "Email should not be Empty")
    @Email(message = "Email invalid")
    private String emailId;

    @NotNull(message = "OTP cannot be null")
    private String otp;
}
