package br.com.estud_io_api.service;

import br.com.estud_io_api.UserDTO;
import br.com.estud_io_api.entity.User;
import br.com.estud_io_api.enums.LanguageOption;
import br.com.estud_io_api.exception.AuthException;
import br.com.estud_io_api.repository.UserRepository;
import br.com.estud_io_api.utils.CustomizedMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    public User createAccount(UserDTO userDTO, int languageOption){
        checkAccount(userDTO,languageOption);
        User user = new User(null,userDTO.getName(),userDTO.getEmail(),userDTO.getPassword(), LocalDate.now(),
                userDTO.getGoal());
        return userRepo.save(user);
    }
    private void checkAccount(UserDTO userDTO, int languageOption){
        if(usernameInUse(userDTO.getName())) {
            throw new AuthException(CustomizedMessageUtils.getCustomMessage(LanguageOption.fromValue(languageOption),
                    "The provided user name is already in use. Please log in to your account or try a different email.",
                    "O nome da conta informado já está sendo usado, logue na sua conta ou tente um diferente email."));
        }
        if(emailInUse(userDTO.getEmail())) {
            throw new AuthException(CustomizedMessageUtils.getCustomMessage(LanguageOption.fromValue(languageOption),
                    "The provided email  is already in use. Please log in to your account or try a different email.",
                    "O email  informado já está sendo usado, logue na sua conta ou tente um diferente email."));
        }

    }

    private boolean usernameInUse(String name) {
        return userRepo.existsByUserName(name);
    }

    private boolean emailInUse(String email){
        return userRepo.existsByEmail(email);
    }
}
