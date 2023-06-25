package ru.shorty.linkshortener.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shorty.linkshortener.dto.LinkCreateDto;
import ru.shorty.linkshortener.dto.LinkUpdateDto;
import ru.shorty.linkshortener.exceptions.*;
import ru.shorty.linkshortener.oauth2.user.UserResolver;
import ru.shorty.linkshortener.services.LinkService;
import ru.shorty.linkshortener.utils.MsgUtil;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/links")
public class LinkController {

    //region Properties && constructor

    private final UserResolver userResolver;

    private final LinkService linkService;

    public LinkController(LinkService linkService, UserResolver userResolver) {
        this.linkService = linkService;
        this.userResolver = userResolver;
    }

    //endregion

    //region Rest methods

    //region Path: /

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        UUID userUid = userResolver.getIdCurrentUser();
        return new ResponseEntity<>(linkService.getAllDtoCast(userUid), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createLink(@Valid @RequestBody LinkCreateDto dto) {
        UUID userUid = userResolver.getIdCurrentUser();
        linkService.createLink(userUid, dto);
        return new ResponseEntity<>(MsgUtil.getSuccess(), HttpStatus.CREATED);
    }

    // endregion

    //region Path: /linkUid

    @GetMapping("/{linkUid}")
    public ResponseEntity<?> getByUid(@PathVariable UUID linkUid) {
        UUID userUid = userResolver.getIdCurrentUser();
        return new ResponseEntity<>(linkService.getByUid(userUid, linkUid), HttpStatus.OK);
    }

    @DeleteMapping("/{linkUid}")
    public ResponseEntity<?> deleteByUid(@PathVariable UUID linkUid) {
        UUID userUid = userResolver.getIdCurrentUser();
        linkService.deleteByUid(userUid, linkUid);
        return new ResponseEntity<>(MsgUtil.getSuccess(), HttpStatus.OK);
    }

    @PutMapping("/{linkUid}")
    public ResponseEntity<?> updateLink(@PathVariable UUID linkUid, @Valid @RequestBody LinkUpdateDto dto) {
        UUID userUid = userResolver.getIdCurrentUser();
        linkService.updateLink(userUid, linkUid, dto);
        return new ResponseEntity<>(MsgUtil.getSuccess(), HttpStatus.OK);
    }

    //endregion

    //region Exceptions Handler

    @ExceptionHandler
    public ResponseEntity<?> linkDoesNotExists(LinkDoesNotExistsException exception) {
        return new ResponseEntity<>(MsgUtil.createError("errorLinkNotExists"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> externalRefIsNotValid(ExternalRefIsNotValidException exception) {
        return new ResponseEntity<>(MsgUtil.createError("errorExternalRefNotValid"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> innerRefAlreadyExists(InnerRefAlreadyExistsException exception) {
        return new ResponseEntity<>(MsgUtil.createError("errorInnerRefExists"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> titleIsNullOrEmpty(TitleIsNullException exception) {
        return new ResponseEntity<>(MsgUtil.createError("titleIsNullOrEmpty"), HttpStatus.BAD_REQUEST);
    }

    //endregion

}
