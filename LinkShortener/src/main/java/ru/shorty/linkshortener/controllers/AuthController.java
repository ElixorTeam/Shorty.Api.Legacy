package ru.shorty.linkshortener.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shorty.linkshortener.oauth2.user.UserResolver;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    UserResolver userResolver;


    @GetMapping("/status")
    public ResponseEntity<?> getAll() {
        String userName = userResolver.getCurrentUser().getName();
        return new ResponseEntity<>(Map.of("user", userName), HttpStatus.OK);
    }

}
