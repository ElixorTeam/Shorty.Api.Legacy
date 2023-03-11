package ru.shorty.linkshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.shorty.linkshortener.model.LinkModel;
import ru.shorty.linkshortener.service.LinkService;

import java.util.List;

@RestController
@RequestMapping("links")
public class LinkController {

    @GetMapping("/test")
    public HttpStatus test() {
        return HttpStatus.OK;
    }

    @Autowired
    private LinkService linkService;

    @PutMapping("")
    public ResponseEntity<LinkModel> updateCustomer(@RequestBody LinkModel link, UriComponentsBuilder builder) {
        HttpHeaders headers = new HttpHeaders();
        if (link == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        linkService.save(link);
        return new ResponseEntity<>(link, headers, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<LinkModel>> get_all() {
        List<LinkModel> links = linkService.listAll();
        if (links.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(links, HttpStatus.OK);
    }
}
