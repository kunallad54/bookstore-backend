package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;

public interface IUserService {

    String registerUser(UserRegistrationDTO userRegistrationDTO);

    String verifyEmail(String tokenID);

    String loginUser(UserLoginDTO userLoginDTO);

    String forgotPassword(String emailID);

    String resetPassword(String token,String password);

}
