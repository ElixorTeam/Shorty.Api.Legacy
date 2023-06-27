package ru.shorty.linkshortener.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppProperties {

    String FrontUrl;
    String FrontRedirectUrl;

    String jwtTokenSecret;
    String authorizedRedirectUrl;
    long tokenExpirationMillis;

}
