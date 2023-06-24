package ru.shorty.linkshortener.oauth2.providers.common;

import java.util.Map;

public abstract class OAuth2UserProvider {
    protected Map<String, Object> attributes;

    public OAuth2UserProvider(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

}
