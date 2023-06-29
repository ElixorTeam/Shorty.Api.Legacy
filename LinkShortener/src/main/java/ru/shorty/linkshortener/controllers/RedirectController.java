package ru.shorty.linkshortener.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shorty.linkshortener.services.AnalyticService;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/redirect")
public class RedirectController {

    AnalyticService redirectService;

    @GetMapping("/{innerRef}")
    public ResponseEntity<?> getExternalRefByInner(HttpServletRequest request, @PathVariable String innerRef) {
        String header = request.getHeader("CLIENT_UID");
        String userAgent = request.getHeader("User-Agent");
        return new ResponseEntity<>(redirectService.getExternalRefByInner(innerRef, userAgent, header), HttpStatus.OK);
    }

}
