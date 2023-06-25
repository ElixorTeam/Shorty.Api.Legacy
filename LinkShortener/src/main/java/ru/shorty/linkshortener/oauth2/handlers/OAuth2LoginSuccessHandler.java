package ru.shorty.linkshortener.oauth2.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import ru.shorty.linkshortener.dto.objects.AuthDto;
import ru.shorty.linkshortener.oauth2.jwt.JwtGenerator;
import ru.shorty.linkshortener.properties.AppProperties;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final JwtGenerator tokenGenerator;

    private final AppProperties appProperties;

    public OAuth2LoginSuccessHandler(AppProperties appProperties, JwtGenerator tokenGenerator) {
        this.appProperties = appProperties;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String redirectUri = appProperties.getAuthorizedRedirectUrl();
        AuthDto token = tokenGenerator.generateTokenDto(authentication);
        logger.debug(token.getAccessToken());
        return UriComponentsBuilder.fromUriString(redirectUri).
            queryParam("jwt", token.getAccessToken()).build().toUriString();
    }
}
