package br.com.estud_io_api.service.auth;

import br.com.estud_io_api.dto.auth.UserDTO;
import br.com.estud_io_api.entity.auth.User;
import br.com.estud_io_api.repository.auth.UserRepository;
import br.com.estud_io_api.utils.MessageHandler;
import br.com.estud_io_api.utils.TokenEmailUtils;
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
        userValidator.checkAccount(userDTO, languageOption);
        User user = new User(null,
                userDTO.getName(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                LocalDate.now(),
                null,
                TokenEmailUtils.generateEmailVerificationToken(),
                false);
        return userRepo.save(user);
    }

}
