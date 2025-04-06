package br.com.estud_io_api.controller;

import br.com.estud_io_api.UserDTO;
import br.com.estud_io_api.exception.AuthException;
import br.com.estud_io_api.service.AuthService;
import br.com.estud_io_api.utils.MessageHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MessageHandler messageHandler;

    @PostMapping("create-account")
    public ResponseEntity<String> createAccount(@RequestBody UserDTO userDTO) {
        int languageHeader = request.getIntHeader("LanguageOption");
        try {
            service.createAccount(userDTO,languageHeader);
            return ResponseEntity.ok(messageHandler.getCustomMessage(languageHeader,
                    "eng-user.created.successfully",
                    "pt-br-user.created.successfully"));
        } catch (AuthException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
