package ru.shorty.linkshortener.configs;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.shorty.linkshortener.oauth2.handlers.OAuth2LoginFailureHandler;
import ru.shorty.linkshortener.oauth2.handlers.OAuth2LoginSuccessHandler;
import ru.shorty.linkshortener.oauth2.jwt.JwtAuthEntryPoint;
import ru.shorty.linkshortener.oauth2.jwt.JwtAuthFilter;
import ru.shorty.linkshortener.oauth2.user.CustomOAuth2UserService;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    CustomOAuth2UserService oauth2UserService;
    OAuth2LoginSuccessHandler oauth2LoginSuccessHandler;
    OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    JwtAuthEntryPoint jwtAuthEntryPoint;
    JwtAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/api/v1/links/**").authenticated()
            .requestMatchers("/api/v1/auth/status").authenticated()
            .anyRequest().permitAll()
            .and()
            .oauth2Login()
            .redirectionEndpoint()
            .baseUri("/oauth2/callback/**")
            .and()
            .userInfoEndpoint()
            .userService(oauth2UserService)
            .and()
            .successHandler(oauth2LoginSuccessHandler)
            .failureHandler(oAuth2LoginFailureHandler)
            .and()
            .logout().permitAll();
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
