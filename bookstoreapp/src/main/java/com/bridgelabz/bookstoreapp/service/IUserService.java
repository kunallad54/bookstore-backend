package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapp.entity.UserRegistration;

public interface IUserService {

    /**
     * Purpose : To add user in database and send OTP on its mail to verify user
     *
     * @param userRegistrationDTO  UserRegistrationDTO object to store it in the repository.
     * @return String Object to print the message.
     */
    String registerUser(UserRegistrationDTO userRegistrationDTO);

    /**
     * Purpose : Ability to verify email after registration.
     *
     * @param userOTP OTP entered by the user
     * @param email user email
     * @return String Object to print the message.
     */
    String verifyEmail(String userOTP,String email);


    /**
     * Purpose : Ability to login user after validating details.
     *
     * @param userLoginDTO Object accepts email and password from user.
     *                     If matches user logs in successfully.
     *
     * @return userLoginDTO object.
     */
    String loginUser(UserLoginDTO userLoginDTO);

    /**
     * Purpose : Ability to send email when user clicks on forget password.
     *
     * @param emailID Variable is matched with the existing emails in the repository.
     *                If match found, a mail is triggered to the user to reset the
     *                password.
     *
     * @return String Object to print the message.
     */

    String forgotPassword(String emailID);

    /**
     * Purpose : Ability to reset the password.
     *
     * @param token Object received from the get url.
     *              The token is further matched with the user email.
     * @param password User sets the password once the token is matched.
     *                 It will update the password of user in repository.
     * @return String Object to print the message.
     */
    String resetPassword(String token,String password);

    /**
     * Purpose : To verify email by the token
     *
     * @param token input given by user
     * @return String object to print whether user was verified or not
     */
    String verifyEmailByToken(String token);
}
