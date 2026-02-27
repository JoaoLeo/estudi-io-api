package br.com.estud_io_api.service.user;

import br.com.estud_io_api.dto.user.UserDetailsDTO;
import br.com.estud_io_api.exception.NotFoundException;
import br.com.estud_io_api.repository.user.UserRepository;
import br.com.estud_io_api.utils.MessageHandler;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MessageHandler messageHandler;

    public UserService(UserRepository userRepository, MessageHandler messageHandler) {
        this.userRepository = userRepository;
        this.messageHandler = messageHandler;
    }

    public UserDetailsDTO getUserDetailsByEmail(String email, Locale locale) {
        return userRepository.getUserDetailsByEmail(email).orElseThrow(
                () -> new NotFoundException(messageHandler.getCustomMessage(locale, "error.user.not.found"))
        );
    }
}
