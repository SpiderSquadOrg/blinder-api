package com.blinder.api.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping
    public ResponseEntity<String> get() {
        return new ResponseEntity<>("Running server 2", HttpStatus.OK);
    }
}

