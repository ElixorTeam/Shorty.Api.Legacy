package ru.shorty.linkshortener.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shorty.linkshortener.oauth2.user.UserResolver;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserResolver userResolver;

    public AuthController(UserResolver userResolver) {
        this.userResolver = userResolver;
    }

    @GetMapping("/status")
    public ResponseEntity<?> getAll() {
        String userName = userResolver.getCurrentUser().getName();
        return new ResponseEntity<>(Map.of("user", userName), HttpStatus.OK);
    }

}
