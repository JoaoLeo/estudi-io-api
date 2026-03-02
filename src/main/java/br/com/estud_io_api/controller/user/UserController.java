package br.com.estud_io_api.controller.user;


import br.com.estud_io_api.dto.auth.PasswordChangeDTO;
import br.com.estud_io_api.dto.user.UserDTO;
import br.com.estud_io_api.dto.user.UserDetailsDTO;
import br.com.estud_io_api.dto.user.UserUpdateDTO;
import br.com.estud_io_api.exception.AuthException;
import br.com.estud_io_api.exception.NotFoundException;
import br.com.estud_io_api.service.user.UserService;
import br.com.estud_io_api.utils.JwtTokenUtil;
import br.com.estud_io_api.utils.LocaleUtils;
import br.com.estud_io_api.utils.MessageHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService service;
    private final JwtTokenUtil jwtTokenUtil;
    private final MessageHandler messageHandler;

    public UserController(UserService service, JwtTokenUtil jwtTokenUtil, MessageHandler messageHandler) {
        this.service = service;
        this.jwtTokenUtil = jwtTokenUtil;
        this.messageHandler = messageHandler;
    }

    @GetMapping("details")
    @Operation(
            summary = "Get user details"
    )
    public ResponseEntity<?> getUserDetailsByEmail(@RequestHeader(name = "Authorization") String token,
                                                   @RequestHeader(name = "Accept-Language", required = false)
                                                   Locale locale) {
        try {
            String email = jwtTokenUtil.extractUsernameWithFullToken(token);
            UserDetailsDTO user = service.getUserDetailsByEmail(email, LocaleUtils.returnLocalFromHeader(locale));
            return ResponseEntity.ok(user);
        } catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("change-password")
    @Operation(
            summary = "Create a user account"
    )
    public ResponseEntity<String> createAccount(@RequestBody PasswordChangeDTO dto,
                                                @RequestHeader(name = "Authorization") String token,
                                                @RequestHeader(name = "Accept-Language", required = false)
                                                Locale locale) {
        try {
            locale = LocaleUtils.returnLocalFromHeader(locale);
            String email = jwtTokenUtil.extractUsernameWithFullToken(token);
            service.changePassword(dto,email, locale);
            return ResponseEntity.ok(messageHandler.getCustomMessage(locale,
                    "user.password.updated"));
        } catch (AuthException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("update")
    @Operation(
            summary = "Update a user account"
    )
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDTO dto,
                                                @RequestHeader(name = "Authorization") String token,
                                                @RequestHeader(name = "Accept-Language", required = false)
                                                Locale locale) {
        locale = LocaleUtils.returnLocalFromHeader(locale);
        String email = jwtTokenUtil.extractUsernameWithFullToken(token);
        service.updateUser(dto,email,locale);
        return ResponseEntity.ok(messageHandler.getCustomMessage(locale,
                "data.updated.successfully"));
    }

}
