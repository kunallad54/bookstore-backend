package com.bridgelabz.bookstoreapp.utility;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Component;

import java.util.Date;


@Slf4j
@Component
public class OTPGenerator {

    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;
    private Date otpRequestedTime;

    /**
     * Purpose : To generate One Time Password
     *
     * @return Random String of OTP
     */
    public String generateOneTimePassword() {
        log.info("Inside generateOTP method");
        String OTP = RandomString.make(6);
        System.out.println(OTP);
        return OTP;
    }

    public boolean isOTPRequired() {
        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = this.otpRequestedTime.getTime();
        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            return false;
        }
        return true;
    }
}
