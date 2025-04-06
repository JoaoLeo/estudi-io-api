package br.com.estud_io_api.service.auth;

import br.com.estud_io_api.dto.auth.LoginDTO;
import br.com.estud_io_api.dto.auth.TokenDTO;
import br.com.estud_io_api.dto.auth.UserDTO;
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

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenEmailUtils tokenUtils;

    @Autowired
    private JwtTokenUtil jwtUtils;

    @Transactional
    public User createAccount(UserDTO userDTO, int languageOption) {
        userValidator.checkAccount(userDTO, languageOption);
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
                messageHandler.getCustomMessage(languageOption,"subject.verify.your.email"),
                messageHandler.getCustomMessageWithParams(
                        languageOption,
                        "text.verify.your.email",
                        tokenUtils.generateLink(token, languageOption)));

        return userRepo.save(user);
    }

    @Transactional
    public void verifyAccount(String token, int languageOption) {
        User user = userRepo.findByEmailToken(token);
        if(user == null)
            throw new AuthException(messageHandler.getCustomMessage(languageOption,
                "user.not.found"));
        user.setEmailVerified(true);
        user.setEmailToken(null);
        userRepo.save(user);
        emailService.sendHtmlEmail(user.getEmail(),
                messageHandler.getCustomMessage(languageOption,"subject.email.verified"),
                messageHandler.getCustomMessage(languageOption,"text.email.verified.html"));
    }

    public TokenDTO login(LoginDTO loginDTO, int languageOption) {
        userValidator.checkValidLogin(loginDTO, languageOption);

        User user = userRepo.findByEmail(loginDTO.getEmail());
        if(passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            throw new LoginException(messageHandler.getCustomMessage(languageOption,
                    "error.invalid.password"));

        TokenDTO token = jwtUtils.generateToken(user);
        return token;
    }
}
