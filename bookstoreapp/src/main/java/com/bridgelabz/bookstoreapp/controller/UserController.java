package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.*;
import com.bridgelabz.bookstoreapp.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@RequestMapping("/user")
@RestController
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private MessageSource messageSource;

    /**
     * Purpose : Ability to insert user details in User Registration repository and
     * sending user OTP on user email to verify user is valid user or not.
     *
     * @param userRegistrationDTO Object of UserRegistrationDTO which will validate user-input
     *                            and once valid, will pass it to the UserRegistration entity.
     *                            Finally, the user-input details gets stored in the Database.
     * @return String Object to print the message whether user was successfully registered or not
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        log.info("Inside registerUser controller method");
        ResponseDTO responseDTO = new ResponseDTO("Registration Successfull",userService.registerUser(userRegistrationDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Purpose : To check whether the user OTP is the actual OTP sent on its mail and then verify user
     *
     * @param verifyUserDTO  object of VerifyUserDTO that ask for email and otp from user
     * @return String object to print message whether user was verified or not
     */
    @PostMapping("/verify-user")
    public ResponseEntity<ResponseDTO> verifyEmail(@RequestBody @Valid VerifyUserDTO verifyUserDTO) {
        log.info("Inside verifyEmail User Controller Method");
        ResponseDTO responseDTO = new ResponseDTO("Verfication in Process",userService.verifyEmail(verifyUserDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    /**
     * Purpose : Ability to login user after validating the user details
     *
     * @param userLoginDTO Object of UserLoginDTO which accepts user email and password/
     *                     If matches then user logged in successfully
     * @return String object to print message whether login was successful or not
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("Inside userLogin Controller Method");
        System.out.println(userLoginDTO.getEmailID());
        System.out.println(userLoginDTO.getPassword());
        ResponseDTO responseDTO = new ResponseDTO(messageSource.getMessage("login.success",
                null, Locale.ENGLISH), userService.loginUser(userLoginDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Purpose : To send email with link for resetting the password if the email is present in DB
     *
     * @param emailID input given by user
     * @return String Object to print message
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDTO> forgetPassword(@RequestParam(name = "emailID") String emailID) {
        log.info(("Inside forgotPassword User Controller Method"));
        ResponseDTO responseDTO = new ResponseDTO("Forgot Password Successfull",userService.forgotPassword(emailID));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Purpose : To reset password by token when it wants to reset the password
     *
     * @param token       entered by user
     * @param passwordDTO is object of PasswordDTO which enters the new password to be set
     * @return String object to print message whether Reset password was successful or not
     */
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam(name = "token") String token,
                                                @Valid @RequestBody PasswordDTO passwordDTO) {
        log.info("Inside resetPassword User Controller Method");
        return new ResponseEntity<>(userService.resetPassword(token, passwordDTO.getPassword()), HttpStatus.OK);
    }

    /**
     * Purpose : To verify email with help of token
     *
     * @param token entered by user
     * @return String object to print message whether user was verified or not
     */
    @GetMapping("/verify-by-token")
    public ResponseEntity<String> verifyEmailByToken(@RequestParam(name = "token") String token) {
        log.info("Inside verifyEmail controller method.");
        return new ResponseEntity<>(userService.verifyEmailByToken(token), HttpStatus.OK);
    }

    /**
     * Purpose : Ability to purchased subscription and send email to user with a link for subscription
     * also set purchased and expiry date.
     *
     * @param token input taken from user
     * @return String object to print message
     */
    @PostMapping("/purchase-subscription")
    public ResponseEntity<String> purchaseSubscription(@RequestParam(name = "token") String token) {
        log.info("Inside purchasedSubscription Controller Method");
        return new ResponseEntity<>(userService.purchaseSubscription(token), HttpStatus.OK);
    }

    /**
     * Purpose: To check whether subscription has expired or not with help of token
     * it will identify the user
     *
     * @param token input taken from user
     * @return String object of messages
     */
    @GetMapping("/expiry-check")
    public ResponseEntity<String> expiryCheck(@RequestParam(name = "token") String token) {
        log.info("Inside expiryCheck Controller Method");
        return new ResponseEntity<>(userService.expiryCheck(token), HttpStatus.OK);
    }
}
