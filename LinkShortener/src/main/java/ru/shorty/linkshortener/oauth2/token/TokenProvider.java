package ru.shorty.linkshortener.oauth2.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.shorty.linkshortener.oauth2.UserPrincipal;
import ru.shorty.linkshortener.properties.AppProperties;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;


@Service
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private final AppProperties appProperties;
    private final String jwtKey;

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
        jwtKey = appProperties.getOauth2().getJwtTokenSecret();
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date currentDate = Date.from(Instant.now());

        return JWT.create()
            .withSubject(String.valueOf(userPrincipal.getUid()))
            .withIssuedAt(currentDate)
            .withExpiresAt(getExpiryDate())
            .sign(Algorithm.HMAC512(jwtKey));
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

    private Date getExpiryDate() {
        Instant nowInstant = Instant.now();
        Instant expiryInstant = nowInstant.plusMillis(appProperties.getOauth2().getTokenExpirationMillis());
        return Date.from(expiryInstant);
    }

    public boolean validateToken(String authToken) {
        try {
            JWT.require(Algorithm.HMAC512(jwtKey)).build().verify(authToken);
            return true;
        } catch (JWTVerificationException ex) {
            logger.error("Invalid JWT token: {}", authToken, ex);
            return false;
        }
    }

}
