package ru.shorty.linkshortener.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shorty.linkshortener.MsgUtil;
import ru.shorty.linkshortener.dto.LinkDto;
import ru.shorty.linkshortener.exceptions.LinkDoesNotExistsException;
import ru.shorty.linkshortener.exceptions.LinkDtoNullException;
import ru.shorty.linkshortener.exceptions.LinkRouteRefAlreadyExistsException;
import ru.shorty.linkshortener.services.LinkService;
import ru.shorty.linkshortener.exceptions.LinkTitleAlreadyExistsException;

import java.util.UUID;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    //region Properties && constructor

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    //endregion

    //region Rest methods

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(linkService.getAllDtoCast(), HttpStatus.OK);
    }

    @GetMapping("/{link_uid}")
    public ResponseEntity<?> getByUid(@PathVariable UUID link_uid) {
        return new ResponseEntity<>(linkService.getByUid(link_uid), HttpStatus.OK);
    }

    @GetMapping("/route_ref/{routeRef}")
    public ResponseEntity<?> getByRef(@PathVariable String routeRef) {
        return new ResponseEntity<>(linkService.getByRouteRef(routeRef), HttpStatus.OK);
    }

    @DeleteMapping("/{link_uid}")
    public ResponseEntity<?> deleteByUid(@PathVariable UUID link_uid) {
        linkService.deleteByUid(link_uid);
        return new ResponseEntity<>(MsgUtil.success(), HttpStatus.OK);
    }

    @PutMapping("/{link_uid}")
    public ResponseEntity<?> updateLink(@PathVariable UUID link_uid, @RequestBody LinkDto dto) {
        linkService.updateLink(link_uid, dto);
        return new ResponseEntity<>(MsgUtil.success(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createLink(@RequestBody LinkDto dto) {
        linkService.createLink(dto);
        return new ResponseEntity<>(MsgUtil.success(), HttpStatus.OK);
    }

    //endregion

    //region Exceptions Handler

    @ExceptionHandler
    public ResponseEntity<?> linkDtoNull(LinkDtoNullException exception) {
        return new ResponseEntity<>(MsgUtil.create("Link title or ref is null"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> LinkTitleAlreadyExists(LinkTitleAlreadyExistsException exception) {
        return new ResponseEntity<>(MsgUtil.create("Link with this title is already exists"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> routeRefAlreadyExists(LinkRouteRefAlreadyExistsException exception) {
        return new ResponseEntity<>(MsgUtil.create("link with this routeRef is already exists"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> linkDoesNotExists(LinkDoesNotExistsException exception) {
        return new ResponseEntity<>(MsgUtil.create("Link doesn't exists"), HttpStatus.BAD_REQUEST);
    }

    //endregion

}
