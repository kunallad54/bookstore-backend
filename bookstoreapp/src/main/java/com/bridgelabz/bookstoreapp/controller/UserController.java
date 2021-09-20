package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.constant.CommonMessage;
import com.bridgelabz.bookstoreapp.dto.PasswordDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapp.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * Purpose : Ability to insert user details in User Registration repository and sending user OTP on user email
     *          to verify user is valid user or not.
     *
     * @param userRegistrationDTO Object of UserRegistrationDTO which will validate user-input
     *                            and once valid, will pass it to the UserRegistration entity.
     *                            Finally, the user-input details gets stored in the Database.
     *
     * @return String Object to print the message whether user was successfully registered or not
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO){
        log.info("Inside registerUser controller method");
        return new ResponseEntity<>(userService.registerUser(userRegistrationDTO), HttpStatus.CREATED);
    }

    /**
     * Purpose : To check whether the user OTP is the actual OTP sent on its mail and then verify user
     *
     * @param OTP input given by user
     * @param email end user email
     * @return String object to print message whether user was verified or not
     */
    @GetMapping
    public ResponseEntity<String> verifyEmail(@RequestParam(name="userOTP") String OTP,
                                              @RequestParam String email) {
        log.info("Inside verifyEmail User Controller Method");
        return new ResponseEntity<>(userService.verifyEmail(OTP,email),HttpStatus.OK);
    }


    /**
     * Purpose : Ability to login user after validating the user details
     *
     * @param userLoginDTO Object of UserLoginDTO which accepts user email and password/
     *                     If matches then user logged in successfully
     *
     * @return String object to print message whether login was successful or not
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@Valid @RequestBody UserLoginDTO userLoginDTO){
        log.info("Inside userLogin Controller Method");
        ResponseDTO responseDTO = new ResponseDTO(CommonMessage.LOGIN_SUCCESSFUL.getMessage(),
                userService.loginUser(userLoginDTO));
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    /**
     * Purpose : To send email with link for resetting the password if the email is present in DB
     *
     * @param emailID input given by user
     * @return String Object to print message
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<String>forgetPassword(@RequestParam(name = "email")String emailID){
        log.info(("Inside forgotPassword User Controller Method"));
        return new ResponseEntity<>(userService.forgotPassword(emailID),HttpStatus.OK);
    }

    /**
     * Purpose : To reset password by token when it wants to reset the password
     *
     * @param token entered by user
     * @param passwordDTO is object of PasswordDTO which enters the new password to be set
     *
     * @return String object to print message whether Reset password was successful or not
     */
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam(name = "token") String token,
                                                @Valid @RequestBody PasswordDTO passwordDTO){
        log.info("Inside resetPassword User Controller Method");
        return new ResponseEntity<>(userService.resetPassword(token,passwordDTO.getPassword()),HttpStatus.OK);
    }

    /**
     * Purpose : To verify email with help of token
     *
     * @param token entered by user
     * @return String object to print message whether user was verified or not
     */
    @GetMapping("/verify-by-token")
    public ResponseEntity<String>verifyEmailByToken(@RequestParam(name = "token") String token){
        log.info("Inside verifyEmail controller method.");
        return new ResponseEntity<>(userService.verifyEmailByToken(token), HttpStatus.OK);
    }
}
