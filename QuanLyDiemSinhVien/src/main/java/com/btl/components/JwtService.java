package com.btl.components;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

  private static final String SECRET_KEY = "11111111111111111111111111111111"; // Temporarily hardcoded

    private static final byte[] SHARED_SECRET_KEY = SECRET_KEY.getBytes();
    private static final int EXPIRE_TIME = 86400000; // 1 day in milliseconds

    public String generateTokenLogin(String username) {
        try {
            JWSSigner signer = new MACSigner(SHARED_SECRET_KEY);

            // Set up JWT claims
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .claim("username", username)
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                    .build();

            // Sign the JWT
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);

            return signedJWT.serialize(); // Return serialized token
        } catch (JOSEException e) {
            logger.error("Error generating token", e);
            return null;
        }
    }

    private JWTClaimsSet getClaimsFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SHARED_SECRET_KEY);

            if (signedJWT.verify(verifier)) {
                return signedJWT.getJWTClaimsSet();
            }
        } catch (JOSEException | ParseException e) {
            logger.error("Error parsing token", e);
        }
        return null;
    }

    private Date getExpirationDateFromToken(String token) {
        JWTClaimsSet claims = getClaimsFromToken(token);
        return (claims != null) ? claims.getExpirationTime() : null;
    }

    public String getUsernameFromToken(String token) {
        try {
            JWTClaimsSet claims = getClaimsFromToken(token);
            return (claims != null) ? claims.getStringClaim("username") : null;
        } catch (ParseException e) {
            logger.error("Error extracting username from token", e);
            return null;
        }
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return (expiration != null) && expiration.before(new Date());
    }

    public Boolean validateTokenLogin(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }

        String username = getUsernameFromToken(token);
        return (username != null && !username.isEmpty() && !isTokenExpired(token));
    }
}

