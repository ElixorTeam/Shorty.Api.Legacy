package ru.shorty.apigateway.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

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

        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

    private boolean isDevActive() {
        String[] activeProfiles = environment.getActiveProfiles();

        for (String profile : activeProfiles)
            if (profile.equalsIgnoreCase("dev"))
                return true;
        return false;
    }

}
