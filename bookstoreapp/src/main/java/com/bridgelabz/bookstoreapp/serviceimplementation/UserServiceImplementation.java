package com.bridgelabz.bookstoreapp.serviceimplementation;

import com.bridgelabz.bookstoreapp.builder.UserServiceBuilder;
import com.bridgelabz.bookstoreapp.constant.CommonMessage;
import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapp.entity.UserRegistration;
import com.bridgelabz.bookstoreapp.exceptions.UserServiceException;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.service.IUserService;
import com.bridgelabz.bookstoreapp.utility.MailUtil;
import com.bridgelabz.bookstoreapp.utility.OTPGenerator;
import com.bridgelabz.bookstoreapp.utility.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
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
//        String email = userRegistrationDTO.getEmailId();
        Optional<UserRegistration> byEmailId = userRepository.findByEmailId(
                userRegistrationDTO.getEmailId());
        if(byEmailId.isPresent()) {
            throw new UserServiceException("User with same email Id already present",
                    UserServiceException.ExceptionType.USER_ALREADY_PRESENT);
        }
        String encodedPassword = bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword());
        userRegistrationDTO.setPassword(encodedPassword);
        UserRegistration userRegistration = userServiceBuilder.buildDO(userRegistrationDTO);
        userRepository.save(userRegistration);
        String OTP = otpGenerator.generateOneTimePassword();
        try {
            mailUtil.sendOTPEmail(userRegistration,OTP);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("registerUser service method successfully executed.");
        return CommonMessage.REGISTRATION_SUCCESSFUL.getMessage();
    }

    @Override
    public String verifyEmail(String tokenID) {
        return null;
    }

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        return null;
    }

    @Override
    public String forgotPassword(String emailID) {
        return null;
    }

    @Override
    public String resetPassword(String token, String password) {
        return null;
    }
}
