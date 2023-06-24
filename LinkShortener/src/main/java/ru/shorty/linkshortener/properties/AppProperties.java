package ru.shorty.linkshortener.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String authorizedRedirectUrl;
    private String jwtTokenSecret;
    private long tokenExpirationMillis;

}
