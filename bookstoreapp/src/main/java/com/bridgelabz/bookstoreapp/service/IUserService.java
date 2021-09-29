package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapp.dto.VerifyUserDTO;

public interface IUserService {

    /**
     * Purpose : To add user in database and send OTP on its mail to verify user
     *
     * @param userRegistrationDTO UserRegistrationDTO object to store it in the repository.
     * @return String Object to print the message.
     */
    String registerUser(UserRegistrationDTO userRegistrationDTO);

    /**
     * Purpose : Ability to verify email after registration.
     *
     * @param verifyUserDTO object of VerifyUserDTO that ask for email and OTP from user
     * @return String Object to print the message.
     */
    String verifyEmail(VerifyUserDTO verifyUserDTO);


    /**
     * Purpose : Ability to login user after validating details.
     *
     * @param userLoginDTO Object accepts email and password from user.
     *                     If matches user logs in successfully.
     * @return userLoginDTO object.
     */
    String loginUser(UserLoginDTO userLoginDTO);

    /**
     * Purpose : Ability to send email when user clicks on forget password.
     *
     * @param emailID Variable is matched with the existing emails in the repository.
     *                If match found, a mail is triggered to the user to reset the
     *                password.
     * @return String Object to print the message.
     */

    String forgotPassword(String emailID);

    /**
     * Purpose : Ability to reset the password.
     *
     * @param token    Object received from the get url.
     *                 The token is further matched with the user email.
     * @param password User sets the password once the token is matched.
     *                 It will update the password of user in repository.
     * @return String Object to print the message.
     */
    String resetPassword(String token, String password);

    /**
     * Purpose : To verify email by the token
     *
     * @param token input given by user
     * @return String object to print whether user was verified or not
     */
    String verifyEmailByToken(String token);

    /**
     * Purpose : Ability to take subscription for particular amount of time and
     * will send email with link to purchase subscription which will
     * generate token to verify to user
     * If user is not verified then it will not able to purchase
     *
     * @param emailId input taken from user
     * @return String object to print message whether user was able to purchase the subscription or not
     */
    String purchaseSubscription(String emailId);

    /**
     * Purpose : To check whether user subcription has expired or not
     * get user by token it has
     *
     * @param token input taken from user
     * @return String object of message whether subscription has expired or not
     */
    String expiryCheck(String token);
}
