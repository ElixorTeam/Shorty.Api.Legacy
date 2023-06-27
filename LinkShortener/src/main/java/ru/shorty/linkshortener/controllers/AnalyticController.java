package ru.shorty.linkshortener.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shorty.linkshortener.exceptions.ExternalRefDoesNotExistsException;
import ru.shorty.linkshortener.services.AnalyticService;
import ru.shorty.linkshortener.utils.MsgUtil;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/links_analytics")
public class AnalyticController {

    private final AnalyticService redirectService;

    public AnalyticController(AnalyticService redirectService) {
        this.redirectService = redirectService;
    }

    @GetMapping("/external_ref_by_inner/{innerRef}")
    public ResponseEntity<?> getExternalRefByInner(HttpServletRequest request, @PathVariable String innerRef) {
        String header = request.getHeader("CLIENT_UID");
        String userAgent = request.getHeader("User-Agent");
        return new ResponseEntity<>(redirectService.getExternalRefByInner(innerRef, userAgent, header), HttpStatus.OK);
    }

    @GetMapping("/{linkUid}")
    public ResponseEntity<?> getBaseAnalytics(@PathVariable UUID linkUid) {
        return new ResponseEntity<>(redirectService.getBaseAnalytics(linkUid), HttpStatus.OK);
    }

    @GetMapping("/time_line/{linkUid}")
    public ResponseEntity<?> getTimeLineAnalytics(@PathVariable UUID linkUid) {
        return new ResponseEntity<>(redirectService.getTimeLineAnalytics(linkUid), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<?> externalRefDoesNotExists(ExternalRefDoesNotExistsException exception) {
        return new ResponseEntity<>(MsgUtil.createError("errorExternalRefNotExists"), HttpStatus.BAD_REQUEST);
    }
}
