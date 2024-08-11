package br.com.estud_io_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class DefaultController {

    @GetMapping()
    public ResponseEntity<String> getDefaultEndpoint(){
        return ResponseEntity.ok("Hello World!");
    }
}
