package com.bridgelabz.bookstoreapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    @NotEmpty(message = "First Name cannot be empty")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]+$", message = "First Name Invalid")
    private String firstName;

    @NotEmpty(message = "Last Name cannot be empty")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]+$", message = "Last Name Invalid")
    private String lastName;

    @NotEmpty(message = "KYC cannot be empty")
    @Pattern(regexp = "^[2-9]{1}[0-9]{11}$", message = "KYC Invalid")
    private String kyc;

    @JsonFormat(pattern = "dd MMM yyyy")
    @NotNull(message = "Date of Birth should not be Empty")
    @Past(message = "Date of Birth should be past date")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "Phone Number can not be empty.")
    @Pattern(regexp = "^(6|7|8|9)?[0-9]{9}$")
    private String phoneNumber;

    @NotNull(message = "Email should not be Empty")
    @Email(message = "Email invalid")
    private String emailId;

    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=[^@#$%^&+=]*[@#$%^&+=][^@#$%^&+=]*$)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password Invalid")
    private String password;

    @Pattern(regexp = "User|Admin", message = "User Type should be Admin or User")
    private String userType;
}
