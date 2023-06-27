package ru.shorty.linkshortener.exceptions.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.shorty.linkshortener.exceptions.common.ExternalRefIsNotValidException;
import ru.shorty.linkshortener.exceptions.common.InnerRefAlreadyExistsException;
import ru.shorty.linkshortener.exceptions.common.LinkDoesNotExistsException;
import ru.shorty.linkshortener.exceptions.common.TitleIsNullException;
import ru.shorty.linkshortener.utils.MsgUtil;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestControllerAdvice
public class LinkExceptionController {


    @ExceptionHandler(value = {LinkDoesNotExistsException.class})
    public ResponseEntity<?> linkDoesNotExists(LinkDoesNotExistsException exception) {
        return new ResponseEntity<>(MsgUtil.createError("errorLinkNotExists"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ExternalRefIsNotValidException.class})
    public ResponseEntity<?> externalRefIsNotValid(ExternalRefIsNotValidException exception) {
        return new ResponseEntity<>(MsgUtil.createError("errorExternalRefNotValid"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InnerRefAlreadyExistsException.class})
    public ResponseEntity<?> innerRefAlreadyExists(InnerRefAlreadyExistsException exception) {
        return new ResponseEntity<>(MsgUtil.createError("errorInnerRefExists"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TitleIsNullException.class})
    public ResponseEntity<?> titleIsNullOrEmpty(TitleIsNullException exception) {
        return new ResponseEntity<>(MsgUtil.createError("titleIsNullOrEmpty"), HttpStatus.BAD_REQUEST);
    }


}
