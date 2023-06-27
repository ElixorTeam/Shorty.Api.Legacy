package ru.shorty.linkshortener.oauth2.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.shorty.linkshortener.dto.common.AuthDto;
import ru.shorty.linkshortener.oauth2.user.CustomUserDetails;
import ru.shorty.linkshortener.properties.AppProperties;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtGenerator {

    private static final Logger logger = LoggerFactory.getLogger(JwtGenerator.class);
    private final AppProperties appProperties;

    public JwtGenerator(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public AuthDto generateTokenDto(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date currentDate = Date.from(Instant.now());
        Date expireDate = getExpiryDate();
        String jwtSecret = appProperties.getJwtTokenSecret();
        return new AuthDto(
            JWT.create()
                .withSubject(String.valueOf(customUserDetails.getUid()))
                .withIssuedAt(currentDate)
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC512(jwtSecret))
        );
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
            return null;
        }
    }

    public boolean validateToken(String authToken) {
        try {
            String jwtSecret = appProperties.getJwtTokenSecret();
            JWT.require(Algorithm.HMAC512(jwtSecret)).build().verify(authToken);
            return true;
        } catch (JWTVerificationException ex) {
            logger.error("Invalid JWT token: {}", authToken, ex);
            throw new AuthenticationCredentialsNotFoundException("Jwt was expired or incorrect");
        }
    }
}
