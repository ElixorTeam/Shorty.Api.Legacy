package ru.shorty.linkshortener.oauth2.providers;

import ru.shorty.linkshortener.oauth2.providers.common.OAuth2UserProvider;

import java.util.Map;

public class GoogleOAuth2User extends OAuth2UserProvider {

    public GoogleOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("sub").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

}
