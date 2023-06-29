package ru.shorty.apigateway.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    private final Environment environment;

    public CorsConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        if (isDevActive())
            config.addAllowedOrigin("*");
        else {
            config.addAllowedOrigin(environment.getProperty("FRONT_DOMAIN"));
            config.addAllowedOrigin(environment.getProperty("FRONT_REDIRECT_DOMAIN"));
        }

        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

    private boolean isDevActive() {
        return Arrays.asList(environment.getActiveProfiles()).contains("dev");
    }

}
