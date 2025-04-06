package br.com.estud_io_api.service;

import br.com.estud_io_api.UserDTO;
import br.com.estud_io_api.entity.User;
import br.com.estud_io_api.exception.AuthException;
import br.com.estud_io_api.repository.UserRepository;
import br.com.estud_io_api.utils.MessageHandler;
import br.com.estud_io_api.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public User createAccount(UserDTO userDTO, int languageOption) {
        checkAccount(userDTO,languageOption);
        User user = new User(null,userDTO.getName(),userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()), LocalDate.now(),
                userDTO.getGoal());
        return userRepo.save(user);
    }

    private void checkAccount(UserDTO userDTO, int languageOption){
        if(userValidator.emailInUse(userDTO.getEmail()))
            throw new AuthException(messageHandler.getCustomMessage(languageOption,
                    "user.already.exists"));

    }
}
