package br.com.estud_io_api.service.user;

import br.com.estud_io_api.dto.auth.PasswordChangeDTO;
import br.com.estud_io_api.dto.user.UserDetailsDTO;
import br.com.estud_io_api.dto.user.UserUpdateDTO;
import br.com.estud_io_api.entity.auth.User;
import br.com.estud_io_api.exception.AuthException;
import br.com.estud_io_api.exception.NotFoundException;
import br.com.estud_io_api.repository.user.UserRepository;
import br.com.estud_io_api.utils.MessageHandler;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MessageHandler messageHandler;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, MessageHandler messageHandler, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.messageHandler = messageHandler;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetailsDTO getUserDetailsByEmail(String email, Locale locale) {
        return userRepository.getUserDetailsByEmail(email).orElseThrow(
                () -> new NotFoundException(messageHandler.getCustomMessage(locale, "error.user.not.found"))
        );
    }

    public void changePassword(PasswordChangeDTO dto,
                               String email,
                               Locale locale) {

        User user = userRepository.findByEmail(email);

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword()))
            throw new AuthException(
                    messageHandler.getCustomMessage(locale, "error.invalid.password")
            );

        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword()))
            throw new AuthException(
                    messageHandler.getCustomMessage(locale,
                            "error.user.password.same.as.current")
            );

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    public void updateUser(UserUpdateDTO dto, String email, Locale locale) {
        User user = userRepository.findByEmail(email);
        boolean updated = false;

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            user.setName(dto.getName());
            updated = true;
        }
        if (dto.getGoal() != null && !dto.getGoal().isEmpty()) {
            user.setGoal(dto.getGoal());
            updated = true;
        }

        if (updated) userRepository.save(user);
    }
}
