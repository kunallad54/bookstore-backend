package com.bridgelabz.bookstoreapp.utility;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TokenUtil {
    @Value("${jwt.secret}")
    private String jwtSecretKey;

    /**
     * Purpose : To generate JWT Token with its expiration time
     *
     * @param email
     * @return
     */
    public String generateVerificationToken(String email) {
        log.info("Inside generateVerificationToken method.");
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTime + 7200000))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public String parseToken(String token) {
        log.info("Inside parseToken method.");
        try {
            return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return "Token is Invalid !!!";
    }
}
