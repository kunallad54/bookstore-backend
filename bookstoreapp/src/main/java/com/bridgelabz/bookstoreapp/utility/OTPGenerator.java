package com.bridgelabz.bookstoreapp.utility;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class OTPGenerator {

    public String generateOneTimePassword() {
        log.info("Inside generateOTP method");
        String OTP = RandomString.make(6);
        System.out.println(OTP);
        return OTP;
    }
}
