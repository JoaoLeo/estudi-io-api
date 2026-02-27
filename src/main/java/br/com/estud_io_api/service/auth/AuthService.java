package br.com.estud_io_api.service.auth;

import br.com.estud_io_api.dto.auth.LoginDTO;
import br.com.estud_io_api.dto.auth.TokenDTO;
import br.com.estud_io_api.dto.user.UserDTO;
import br.com.estud_io_api.entity.auth.User;
import br.com.estud_io_api.exception.AuthException;
import br.com.estud_io_api.exception.LoginException;
import br.com.estud_io_api.repository.auth.UserRepository;
import br.com.estud_io_api.service.email.EmailService;
import br.com.estud_io_api.utils.JwtTokenUtil;
import br.com.estud_io_api.utils.MessageHandler;
import br.com.estud_io_api.utils.TokenEmailUtils;
import br.com.estud_io_api.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Locale;

@Service
public class AuthService {

    private final UserRepository userRepo;

    private final UserValidator userValidator;

    private final MessageHandler messageHandler;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final TokenEmailUtils tokenUtils;

    private final JwtTokenUtil jwtUtils;

    public AuthService(UserRepository userRepo, UserValidator userValidator, MessageHandler messageHandler, PasswordEncoder passwordEncoder, EmailService emailService, TokenEmailUtils tokenUtils, JwtTokenUtil jwtUtils) {
        this.userRepo = userRepo;
        this.userValidator = userValidator;
        this.messageHandler = messageHandler;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.tokenUtils = tokenUtils;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public User createAccount(UserDTO userDTO, Locale locale) {
        userValidator.checkAccount(userDTO, locale);
        String token = tokenUtils.generateEmailVerificationToken();
        User user = new User(null,
                userDTO.getName(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                LocalDate.now(),
                null,
                token,
                false);

        emailService.sendSimpleEmail(userDTO.getEmail(),
                messageHandler.getCustomMessage(locale,"subject.verify.your.email"),
                messageHandler.getCustomMessageWithParams(
                        locale,
                        "text.verify.your.email",
                        tokenUtils.generateLink(token, locale)));

        return userRepo.save(user);
    }

    @Transactional
    public void verifyAccount(String token, Locale locale) {
        User user = userRepo.findByEmailToken(token);
        if(user == null)
            throw new AuthException(messageHandler.getCustomMessage(locale,
                "user.not.found"));
        user.setEmailVerified(true);
        user.setEmailToken(null);
        userRepo.save(user);
        emailService.sendHtmlEmail(user.getEmail(),
                messageHandler.getCustomMessage(locale,"subject.email.verified"),
                messageHandler.getCustomMessage(locale,"text.email.verified.html"));
    }

    public TokenDTO login(LoginDTO loginDTO, Locale locale) {
        userValidator.checkValidLogin(loginDTO, locale);

        User user = userRepo.findByEmail(loginDTO.getEmail());
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            throw new LoginException(messageHandler.getCustomMessage(locale,
                    "error.invalid.password"));

        TokenDTO token = jwtUtils.generateToken(user);
        return token;
    }
}
