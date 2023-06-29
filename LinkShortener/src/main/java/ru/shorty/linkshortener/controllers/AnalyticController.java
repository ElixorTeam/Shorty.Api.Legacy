package ru.shorty.linkshortener.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shorty.linkshortener.services.AnalyticService;

import java.util.UUID;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/links_analytics")
public class AnalyticController {

    AnalyticService redirectService;

    @GetMapping("/{linkUid}")
    public ResponseEntity<?> getBaseAnalytics(@PathVariable UUID linkUid) {
        return new ResponseEntity<>(redirectService.getBaseAnalytics(linkUid), HttpStatus.OK);
    }

    @GetMapping("/time_line/{linkUid}")
    public ResponseEntity<?> getTimeLineAnalytics(@PathVariable UUID linkUid) {
        return new ResponseEntity<>(redirectService.getTimeLineAnalytics(linkUid), HttpStatus.OK);
    }
}
