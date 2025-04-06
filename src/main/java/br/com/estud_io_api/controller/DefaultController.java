package br.com.estud_io_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class DefaultController {

    @GetMapping("hello-world")
    public ResponseEntity<String> getDefaultEndpoint(){
        return ResponseEntity.ok("Hello World!");
    }
}
