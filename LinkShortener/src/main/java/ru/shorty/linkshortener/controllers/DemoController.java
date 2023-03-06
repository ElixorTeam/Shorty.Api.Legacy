package ru.shorty.linkshortener.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("demo")
public class DemoController {


    @GetMapping("hello_world")
    public ResponseEntity<?> helloWorld(){
        return new ResponseEntity<>(Map.of("result", "Hello world!"), HttpStatus.OK);
    }

}
