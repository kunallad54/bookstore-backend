package com.bridgelabz.bookstoreapp.serviceimplementation;

import com.bridgelabz.bookstoreapp.builder.UserServiceBuilder;
import com.bridgelabz.bookstoreapp.constant.CommonMessage;
import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapp.entity.UserRegistration;
import com.bridgelabz.bookstoreapp.exceptions.BookStoreException;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.service.IUserService;
import com.bridgelabz.bookstoreapp.utility.MailUtil;
import com.bridgelabz.bookstoreapp.utility.OTPGenerator;
import com.bridgelabz.bookstoreapp.utility.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImplementation implements IUserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceBuilder userServiceBuilder;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    OTPGenerator otpGenerator;


    @Override
    public String registerUser(UserRegistrationDTO userRegistrationDTO) {
        log.info("Inside registerUser userImplementationService Method");
        Optional<UserRegistration> byEmailId = userRepository.findByEmailId(
                userRegistrationDTO.getEmailId());
        if (byEmailId.isPresent()) {
            throw new BookStoreException("User with same email Id already present",
                    BookStoreException.ExceptionType.USER_ALREADY_PRESENT);
        }
        String encodedPassword = bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword());
        userRegistrationDTO.setPassword(encodedPassword);
        UserRegistration userRegistration = userServiceBuilder.buildDO(userRegistrationDTO);
        String OTP = otpGenerator.generateOneTimePassword();
        userRegistration.setOtp(OTP);
        userRepository.save(userRegistration);
        mailUtil.sendOTPEmail(userRegistration, OTP);
        log.info("registerUser service method successfully executed.");
        return CommonMessage.REGISTRATION_SUCCESSFUL.getMessage();
    }


    @Override
    public String verifyEmail(String userOTP, String email) {
        log.info("Inside verifyEmail User Service Method");
        UserRegistration userRegistration = getUserByEmail(email);
        String generatedOTP = userRegistration.getOtp();
        if (Objects.equals(userOTP, generatedOTP)) {
            userRegistration.setIsVerified(true);
            userRepository.save(userRegistration);
            return CommonMessage.EMAIL_VERIFIED.getMessage();
        }
        return CommonMessage.EMAIL_NOT_VERIFIED.getMessage();
    }

    /**
     * Purpose : To get user details by fetching its email
     *
     * @param email input given by user
     * @return object of the UserRegistration i.e data of the user
     */
    public UserRegistration getUserByEmail(String email) {
        return userRepository.findByEmailId(email)
                .orElseThrow(() -> new BookStoreException
                        ("Unauthorised User", BookStoreException.ExceptionType.UNAUTHORISED_USER));
    }

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        log.info("Inside loginUser Service method ");
        UserRegistration userByEmail = getUserByEmail(userLoginDTO.getEmailID());
        if (userByEmail.isVerified) {
            boolean password = bCryptPasswordEncoder.matches(userLoginDTO.getPassword(),
                    userByEmail.getPassword());
            if (!password) {
                throw new BookStoreException("Incorrect Password",
                        BookStoreException.ExceptionType.PASSWORD_INCORRECT);
            }
            log.info("loginUser Service Method Successfully executed");
            return userLoginDTO.getEmailID();
        } else {
            throw new BookStoreException("Not Verified",
                    BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    @Override
    public String forgotPassword(String emailID) {
        log.info("Inside forgotPassword User Service Method");
        UserRegistration userByEmail = getUserByEmail(emailID);
        if (userByEmail.isVerified) {
            try {
                String displayMessage = "RESET PASSWORD";
                mailUtil.sendResetPasswordMail(userByEmail,tokenUtil.generateVerificationToken(emailID),displayMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new BookStoreException("Not Verified", BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
        log.info("forgotPassword Service Method executed successfully");
        return CommonMessage.FORGET_PASSWORD.getMessage();
    }

    @Override
    public String resetPassword(String token, String password) {
        log.info("Inside resetPassword User Service Method");
        UserRegistration userByEmailToken = getUserByEmailToken(token);
        userByEmailToken.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(userByEmailToken);
        log.info("resetPassword Service Method Executed Successfully");
        return CommonMessage.RESET_PASSWORD.getMessage();
    }

    /**
     * Purpose : To get user details with help of token
     *
     * @param token input by user
     * @return UserRegistration object
     */
    public UserRegistration getUserByEmailToken(String token) {
        String email = tokenUtil.parseToken(token);
        return getUserByEmail(email);
    }

    @Override
    public String verifyEmailByToken(String token) {
        log.info("Inside verifyEmail service method.");
        UserRegistration userRegistration = getUserByEmailToken(token);
        userRegistration.setIsVerified(true);
        userRepository.save(userRegistration);
        log.info("verifyEmail service method successfully executed.");
        return CommonMessage.EMAIL_VERIFIED.getMessage();
    }

}
