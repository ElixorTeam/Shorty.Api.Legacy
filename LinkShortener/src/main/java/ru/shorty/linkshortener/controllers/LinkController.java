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
        return new ResponseEntity<>(linkService.getAllDtoCast(userResolver.getIdCurrentUser()), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createLink(@Valid @RequestBody LinkCreateDto dto) {
        linkService.createLink(userResolver.getIdCurrentUser(), dto);
        return new ResponseEntity<>(MsgUtil.getSuccess(), HttpStatus.CREATED);
    }

    // endregion

    //region Path: /linkUid

    @GetMapping("/{linkUid}")
    public ResponseEntity<?> getByUid(@PathVariable UUID linkUid) {
        return new ResponseEntity<>(linkService.getByUid(userResolver.getIdCurrentUser(), linkUid), HttpStatus.OK);
    }

    @DeleteMapping("/{linkUid}")
    public ResponseEntity<?> deleteByUid(@PathVariable UUID linkUid) {
        linkService.deleteByUid(userResolver.getIdCurrentUser(), linkUid);
        return new ResponseEntity<>(MsgUtil.getSuccess(), HttpStatus.OK);
    }

    @PutMapping("/{linkUid}")
    public ResponseEntity<?> updateLink(@PathVariable UUID linkUid, @Valid @RequestBody LinkUpdateDto dto) {
        linkService.updateLink(userResolver.getIdCurrentUser(), linkUid, dto);
        return new ResponseEntity<>(MsgUtil.getSuccess(), HttpStatus.OK);
    }

    //endregion

    @GetMapping("/external_ref_by_inner/{innerRef}")
    public ResponseEntity<?> getExternalRefByInner(@PathVariable String innerRef) {
        return new ResponseEntity<>(linkService.getExternalRefByInner(innerRef), HttpStatus.OK);
    }

    //endregion

    //region Exceptions Handler

    @ExceptionHandler
    public ResponseEntity<?> linkDoesNotExists(LinkDoesNotExistsException exception) {
        return new ResponseEntity<>(MsgUtil.createError("errorLinkNotExists"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> externalRefDoesNotExists(ExternalRefDoesNotExistsException exception) {
        return new ResponseEntity<>(MsgUtil.createError("errorExternaRefNotExists"), HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> defaultTitleCanNotSet(DefaultTitleCanNotSetException exception) {
        return new ResponseEntity<>(MsgUtil.createError("errorDefaultTitleNotSet"), HttpStatus.BAD_REQUEST);
    }

    //endregion

}
