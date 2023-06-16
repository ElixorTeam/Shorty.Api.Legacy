package ru.shorty.linkshortener.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.shorty.linkshortener.oauth2.CustomOAuth2UserService;
import ru.shorty.linkshortener.oauth2.handlers.OAuth2LoginFailureHandler;
import ru.shorty.linkshortener.oauth2.handlers.OAuth2LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService oauth2UserService;

    private final OAuth2LoginSuccessHandler oauth2LoginSuccessHandler;

    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    public SecurityConfig(CustomOAuth2UserService oauth2UserService, OAuth2LoginSuccessHandler oauth2LoginSuccessHandler, OAuth2LoginFailureHandler oAuth2LoginFailureHandler) {
        this.oauth2UserService = oauth2UserService;
        this.oauth2LoginSuccessHandler = oauth2LoginSuccessHandler;
        this.oAuth2LoginFailureHandler = oAuth2LoginFailureHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            .and()
            .oauth2Login()
                .redirectionEndpoint()
                    .baseUri("/oauth2/callback/*")
                    .and()
                .userInfoEndpoint()
                .userService(oauth2UserService)
            .and()
            .successHandler(oauth2LoginSuccessHandler)
            .failureHandler(oAuth2LoginFailureHandler)
            .and()
            .logout().permitAll();
        return http.build();
    }

}
