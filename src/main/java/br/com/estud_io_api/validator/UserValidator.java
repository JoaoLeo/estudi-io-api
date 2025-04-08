package br.com.estud_io_api.validator;

import br.com.estud_io_api.dto.auth.LoginDTO;
import br.com.estud_io_api.dto.auth.UserDTO;
import br.com.estud_io_api.entity.auth.User;
import br.com.estud_io_api.exception.AuthException;
import br.com.estud_io_api.exception.LoginException;
import br.com.estud_io_api.repository.auth.UserRepository;
import br.com.estud_io_api.utils.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class UserValidator {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MessageHandler messageHandler;

    private boolean emailInUse(String email){
        return userRepo.existsByEmail(email);
    }

    public void checkAccount(UserDTO userDTO, Locale locale){
        if(emailInUse(userDTO.getEmail()))
            throw new AuthException(messageHandler.getCustomMessage(locale,
                    "user.already.exists"));

    }

    public void checkValidLogin(LoginDTO loginDTO, Locale locale) {
        if(loginDTO == null || loginDTO.getEmail() == null || loginDTO.getPassword() == null)
            throw new AuthException(messageHandler.getCustomMessage(locale,
                    "error.invalid.data"));

        User user = userRepo.findByEmail(loginDTO.getEmail());
        if(user == null)
            throw new AuthException(messageHandler.getCustomMessage(locale,
                    "email.user.not.found"));

        if(user.getEmailVerified() == null || !user.getEmailVerified())
            throw new LoginException(messageHandler.getCustomMessage(locale,
                    "error.email.not.verified"));

    }
}
