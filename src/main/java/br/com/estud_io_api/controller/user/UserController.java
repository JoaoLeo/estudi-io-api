package br.com.estud_io_api.controller.user;


import br.com.estud_io_api.dto.user.UserDetailsDTO;
import br.com.estud_io_api.exception.AuthException;
import br.com.estud_io_api.exception.NotFoundException;
import br.com.estud_io_api.service.user.UserService;
import br.com.estud_io_api.utils.JwtTokenUtil;
import br.com.estud_io_api.utils.MessageHandler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService service;
    private final JwtTokenUtil jwtTokenUtil;

    public UserController(UserService service, JwtTokenUtil jwtTokenUtil) {
        this.service = service;
        this.jwtTokenUtil = jwtTokenUtil;
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
            UserDetailsDTO user = service.getUserDetailsByEmail(email, locale);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


}
