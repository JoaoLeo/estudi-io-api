package br.com.estud_io_api.controller.auth;

import br.com.estud_io_api.dto.auth.UserDTO;
import br.com.estud_io_api.exception.AuthException;
import br.com.estud_io_api.service.auth.AuthService;
import br.com.estud_io_api.utils.MessageHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "Create a user account"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<String> createAccount(@RequestBody UserDTO userDTO) {
        int languageHeader = request.getIntHeader("LanguageOption");
        try {
            service.createAccount(userDTO,languageHeader);
            return ResponseEntity.ok(messageHandler.getCustomMessage(languageHeader,
                    "user.created.successfully"));
        } catch (AuthException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token,
                                                @RequestParam("languageOption") Integer languageHeader) {
        try {
            service.verifyAccount(token, languageHeader);
            return ResponseEntity.ok(messageHandler.getCustomMessage(languageHeader,
                    "subject.email.verified"));
        } catch (AuthException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
