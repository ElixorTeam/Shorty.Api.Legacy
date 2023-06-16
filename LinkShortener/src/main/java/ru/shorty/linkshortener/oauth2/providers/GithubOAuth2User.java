package ru.shorty.linkshortener.oauth2.providers;

import ru.shorty.linkshortener.oauth2.providers.common.OAuth2UserProvider;

import java.util.Map;

public class GithubOAuth2User extends OAuth2UserProvider {

    public GithubOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

}
