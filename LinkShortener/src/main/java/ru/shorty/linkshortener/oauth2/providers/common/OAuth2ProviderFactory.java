package ru.shorty.linkshortener.oauth2.providers.common;

import ru.shorty.linkshortener.oauth2.providers.GithubOAuth2User;
import ru.shorty.linkshortener.oauth2.providers.GoogleOAuth2User;

import java.util.Map;

public class OAuth2ProviderFactory {

    public static OAuth2UserProvider getOAuth2User(String registrationId, Map<String, Object> attributes) throws Exception {
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2User(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
            return new GithubOAuth2User(attributes);
        } else {
            throw new Exception("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
