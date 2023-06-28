package ru.shorty.linkshortener.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.shorty.linkshortener.dto.common.LinkDto;
import ru.shorty.linkshortener.dto.rules.ValidationRules;
import ru.shorty.linkshortener.dto.rules.ViewAccess;
import ru.shorty.linkshortener.oauth2.user.UserResolver;
import ru.shorty.linkshortener.services.LinkService;
import ru.shorty.linkshortener.utils.MsgUtil;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/links")
public class LinkController {

    //region Properties

    UserResolver userResolver;

    LinkService linkService;

    //endregion

    //region Path: /

    @GetMapping
    @JsonView(ViewAccess.View.class)
    public ResponseEntity<?> getAll() {
        UUID userUid = userResolver.getIdCurrentUser();
        return new ResponseEntity<>(linkService.getAllDtoCast(userUid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createLink(@Validated(ValidationRules.Create.class) @RequestBody LinkDto dto) {
        UUID userUid = userResolver.getIdCurrentUser();
        linkService.createLink(userUid, dto);
        return new ResponseEntity<>(MsgUtil.getSuccess(), HttpStatus.CREATED);
    }

    // endregion

    // region Path: /linkUid

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
    public ResponseEntity<?> updateLink(@PathVariable UUID linkUid, @Validated(ValidationRules.Update.class) @RequestBody LinkDto dto) {
        UUID userUid = userResolver.getIdCurrentUser();
        linkService.updateLink(userUid, linkUid, dto);
        return new ResponseEntity<>(MsgUtil.getSuccess(), HttpStatus.OK);
    }

    //endregion

}
