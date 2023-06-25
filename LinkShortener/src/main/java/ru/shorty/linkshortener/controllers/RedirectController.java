package ru.shorty.linkshortener.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shorty.linkshortener.exceptions.ExternalRefDoesNotExistsException;
import ru.shorty.linkshortener.services.RedirectService;
import ru.shorty.linkshortener.utils.MsgUtil;


@RestController
@RequestMapping("/api/v1/links_analytics")
public class RedirectController {

    private final RedirectService redirectService;

    public RedirectController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    @GetMapping("/external_ref_by_inner/{innerRef}")
    public ResponseEntity<?> getExternalRefByInner(HttpServletRequest request, @PathVariable String innerRef) {
        String header = request.getHeader("CLIENT_UID");
        String userAgent = request.getHeader("User-Agent");
        return new ResponseEntity<>(redirectService.getExternalRefByInner(innerRef, userAgent, header), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<?> externalRefDoesNotExists(ExternalRefDoesNotExistsException exception) {
        return new ResponseEntity<>(MsgUtil.createError("errorExternalRefNotExists"), HttpStatus.BAD_REQUEST);
    }
}
