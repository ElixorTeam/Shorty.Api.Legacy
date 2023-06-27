package ru.shorty.linkshortener.exceptions.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shorty.linkshortener.exceptions.common.ExternalRefDoesNotExistsException;
import ru.shorty.linkshortener.utils.MsgUtil;

@RestControllerAdvice
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AnalyticExceptionController {
    @ExceptionHandler(value = {ExternalRefDoesNotExistsException.class})
    public ResponseEntity<?> externalRefDoesNotExists(ExternalRefDoesNotExistsException exception) {
        return new ResponseEntity<>(MsgUtil.createError("errorExternalRefNotExists"), HttpStatus.BAD_REQUEST);
    }
}
