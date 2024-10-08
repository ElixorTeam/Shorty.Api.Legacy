package ru.shorty.linkshortener.oauth2.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.shorty.linkshortener.exceptions.common.JwtTokenValidException;
import ru.shorty.linkshortener.oauth2.user.CustomUserDetails;
import ru.shorty.linkshortener.properties.AppProperties;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JwtGenerator {

    static Logger logger = LoggerFactory.getLogger(JwtGenerator.class);
    AppProperties appProperties;


    public String generateJWT(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date currentDate = Date.from(Instant.now());
        Date expireDate = getExpiryDate();
        String jwtSecret = appProperties.getJwtTokenSecret();
        return JWT.create()
            .withSubject(String.valueOf(customUserDetails.getUid()))
            .withIssuedAt(currentDate)
            .withExpiresAt(expireDate)
            .sign(Algorithm.HMAC512(jwtSecret));
    }

    private Date getExpiryDate() {
        Instant nowInstant = Instant.now();
        Instant expiryInstant = nowInstant.plusMillis(appProperties.getTokenExpirationMillis());
        return Date.from(expiryInstant);
    }

    public UUID getUserIdFromToken(String jwtToken) {
        try {
            DecodedJWT decodedJWT = JWT.decode(jwtToken);
            return UUID.fromString(decodedJWT.getSubject());
        } catch (JWTDecodeException e) {
            logger.error("Invalid JWT token: {}", jwtToken, e);
            throw new JwtTokenValidException();
        }
    }

    public boolean validateToken(String authToken) {
        try {
            String jwtSecret = appProperties.getJwtTokenSecret();
            JWT.require(Algorithm.HMAC512(jwtSecret)).build().verify(authToken);
            return true;
        } catch (JWTVerificationException ex) {
            logger.error("Invalid JWT token: {}", authToken, ex);
            throw new JwtTokenValidException();
        }
    }
}
