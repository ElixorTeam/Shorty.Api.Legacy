package ru.shorty.linkshortener.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String FrontUrl;
    private String FrontRedirectUrl;

    private String jwtTokenSecret;
    private String authorizedRedirectUrl;
    private long tokenExpirationMillis;

}
