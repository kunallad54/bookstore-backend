package com.bridgelabz.bookstoreapp.serviceimplementation;

import com.bridgelabz.bookstoreapp.builder.UserServiceBuilder;
import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.dto.UserRegistrationDTO;
import com.bridgelabz.bookstoreapp.entity.User;
import com.bridgelabz.bookstoreapp.exceptions.BookStoreException;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.service.IUserService;
import com.bridgelabz.bookstoreapp.utility.MailUtil;
import com.bridgelabz.bookstoreapp.utility.OTPGenerator;
import com.bridgelabz.bookstoreapp.utility.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
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

    @Autowired
    MessageSource messageSource;

    /**
     * Purpose : Ability to register user after validating user details and send OTP on
     *           user email to verify user
     *
     * @param userRegistrationDTO UserRegistrationDTO object to store it in the repository.
     * @return String object of message
     */
    @Override
    public String registerUser(UserRegistrationDTO userRegistrationDTO) {
        log.info("Inside registerUser userImplementationService Method");
        Optional<User> byEmailId = userRepository.findByEmailId(
                userRegistrationDTO.getEmailId());
        if (byEmailId.isPresent()) {
            throw new BookStoreException(messageSource.getMessage("user.exist",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.USER_ALREADY_PRESENT);
        }
        String encodedPassword = bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword());
        userRegistrationDTO.setPassword(encodedPassword);
        User user = userServiceBuilder.buildDO(userRegistrationDTO);
        String OTP = otpGenerator.generateOneTimePassword();
        user.setOtp(OTP);
        userRepository.save(user);
        mailUtil.sendOTPEmail(user, OTP);
        log.info("registerUser service method successfully executed.");
        return messageSource.getMessage("registration.success", null, Locale.ENGLISH);
    }


    /**
     * Purpose :  Ability to verify email after registration
     *            If user entered OTP and actual OTP matches then
     *            user will be verified or else not
     *
     * @param userOTP OTP entered by the user
     * @param email   user email
     * @return String object of message
     */
    @Override
    public String verifyEmail(String userOTP, String email) {
        log.info("Inside verifyEmail User Service Method");
        User user = getUserByEmail(email);
        String generatedOTP = user.getOtp();
        if (Objects.equals(userOTP, generatedOTP)) {
            user.setIsVerified(true);
            userRepository.save(user);
            return messageSource.getMessage("email.verified", null, Locale.ENGLISH);
        }
        return messageSource.getMessage("email.not.verified", null, Locale.ENGLISH);
    }

    /**
     * Purpose : Ability to login user after validating details.
     *
     * @param userLoginDTO Object accepts email and password from user.
     *                     If matches user logs in successfully.
     * @return
     */
    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        log.info("Inside loginUser Service method ");
        User userByEmail = getUserByEmail(userLoginDTO.getEmailID());
        if (userByEmail.isVerified) {
            boolean password = bCryptPasswordEncoder.matches(userLoginDTO.getPassword(),
                    userByEmail.getPassword());
            if (!password) {
                throw new BookStoreException(messageSource.getMessage("incorrect.password",
                        null, Locale.ENGLISH), BookStoreException.ExceptionType.PASSWORD_INCORRECT);
            }
            log.info("loginUser Service Method Successfully executed");
            return userLoginDTO.getEmailID();
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : Ability to send email when user clicks on forget password.
     *
     * @param emailID Variable is matched with the existing emails in the repository.
     *                If match found, a mail is triggered to the user to reset the
     *                password.
     * @return String object of message
     */
    @Override
    public String forgotPassword(String emailID) {
        log.info("Inside forgotPassword User Service Method");
        User userByEmail = getUserByEmail(emailID);
        if (userByEmail.isVerified) {
            try {
                String displayMessage = "RESET PASSWORD";
                mailUtil.sendResetPasswordMail(userByEmail, tokenUtil.generateVerificationToken(emailID), displayMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
        log.info("forgotPassword Service Method executed successfully");
        return messageSource.getMessage("forgot.password", null, Locale.ENGLISH);
    }

    /**
     * Purpose : Ability to reset the password.
     *
     * @param token    Object received from the get url.
     *                 The token is further matched with the user email.
     * @param password User sets the password once the token is matched.
     *                 It will update the password of user in repository.
     * @return String object of message
     */
    @Override
    public String resetPassword(String token, String password) {
        log.info("Inside resetPassword User Service Method");
        User userByEmailToken = getUserByEmailToken(token);
        userByEmailToken.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(userByEmailToken);
        log.info("resetPassword Service Method Executed Successfully");
        return messageSource.getMessage("reset.password", null, Locale.ENGLISH);
    }

    /**
     * Purpose : To verify email by the token
     *
     * @param token input given by user
     * @return String object to print whether user was verified or not
     */
    @Override
    public String verifyEmailByToken(String token) {
        log.info("Inside verifyEmail service method.");
        User user = getUserByEmailToken(token);
        user.setIsVerified(true);
        userRepository.save(user);
        log.info("verifyEmail service method successfully executed.");
        return messageSource.getMessage("email.verified", null, Locale.ENGLISH);
    }

    /**
     * Purpose : Ability to take subscription for particular amount of time and
     *           will send email with link to purchase subscription which will
     *           generate token to verify to user.If user is not verified then
     *           it will not able to purchase
     *
     * @param emailId input taken from user
     * @return String object to print message whether user was able to purchase the subscription or not
     */
    @Override
    public String purchaseSubscription(String emailId) {
        log.info("Inside purchaseSubscription Service Method");
        User userByEmail = getUserByEmail(emailId);
        userByEmail.setPurchasedDate(LocalDateTime.now());
        userByEmail.setExpiredDate(userByEmail.getPurchasedDate().plusMinutes(5));
        userRepository.save(userByEmail);
        if (userByEmail.isVerified) {
            String displayMessage = "PURCHASE SUBSCRIPTION";
            mailUtil.sendSubscriptionMail(userByEmail,
                    tokenUtil.generateVerificationToken(emailId), displayMessage);
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
        return messageSource.getMessage("purchased.success", null, Locale.ENGLISH);
    }

    /**
     * Purpose : To check whether user subscription has expired or not
     *           get user by token it has
     *
     * @param token input taken from user
     * @return String object of message whether subscription has expired or not
     */
    @Override
    public String expiryCheck(String token) {
        log.info("Inside expiryCheck Service Method");
        User userByEmailToken = getUserByEmailToken(token);
        log.info(userByEmailToken.getEmailId());
        LocalDateTime getCurrentTime = LocalDateTime.now();
        log.info(String.valueOf(getCurrentTime));
        System.out.println(userByEmailToken.getExpiredDate());
        if (userByEmailToken.isVerified) {
            if (userByEmailToken.getExpiredDate().isAfter(getCurrentTime)) {
                return "Your Subscription will expired on " + userByEmailToken.getExpiredDate();
            } else {
                return messageSource.getMessage("expired.success", null, Locale.ENGLISH) + " on " + userByEmailToken.getExpiredDate();
            }
        } else {
            throw new BookStoreException(messageSource.getMessage("email.not.verified",
                    null, Locale.ENGLISH), BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }

    }

    /**
     * Purpose : To get user details with help of token
     *
     * @param token input by user
     * @return UserRegistration object
     */
    public User getUserByEmailToken(String token) {
        String email = tokenUtil.parseToken(token);
        return getUserByEmail(email);
    }

    /**
     * Purpose : To get user details by fetching its email
     *
     * @param email input given by user
     * @return object of the UserRegistration i.e data of the user
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmailId(email)
                .orElseThrow(() -> new BookStoreException
                        (messageSource.getMessage("unauthorised.user", null, Locale.ENGLISH),
                                BookStoreException.ExceptionType.UNAUTHORISED_USER));
    }

}
