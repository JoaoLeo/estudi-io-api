package br.com.estud_io_api.controller.auth;

import br.com.estud_io_api.dto.auth.LoginDTO;
import br.com.estud_io_api.dto.auth.TokenDTO;
import br.com.estud_io_api.dto.auth.UserDTO;
import br.com.estud_io_api.exception.AuthException;
import br.com.estud_io_api.exception.LoginException;
import br.com.estud_io_api.service.auth.AuthService;
import br.com.estud_io_api.utils.MessageHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatusCode;
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
    @Operation(
            summary = "Verify a user account"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully verified"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
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

    @PostMapping("login")
    @Operation(
            summary = "Login"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login succeeded"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g., malformed JSON)"),
            @ApiResponse(responseCode = "401", description = "Authentication failed (e.g., incorrect password, email not verified)")
    })
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        int languageHeader = request.getIntHeader("LanguageOption");
        try {
            TokenDTO token = service.login(loginDTO, languageHeader);
            return ResponseEntity.ok(token);
        } catch (LoginException e){
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(e.getMessage());
        } catch (AuthException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
