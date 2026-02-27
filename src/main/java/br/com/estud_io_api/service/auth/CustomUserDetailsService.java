package br.com.estud_io_api.service.auth;

import br.com.estud_io_api.entity.auth.CustomUserDetails;
import br.com.estud_io_api.entity.auth.User;
import br.com.estud_io_api.repository.auth.UserRepository;
import br.com.estud_io_api.utils.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CustomUserDetailsService  {

    private final UserRepository repository;

    private final MessageHandler messageHandler;

    public CustomUserDetailsService(UserRepository repository, MessageHandler messageHandler) {
        this.repository = repository;
        this.messageHandler = messageHandler;
    }


    public UserDetails loadUserByEmail(String name, Locale locale) {
        User user = repository.findByEmail(name);
        if (user == null) {
           throw new UsernameNotFoundException(
                    messageHandler.getCustomMessage(null,"error.user.not.found")
           );
        }
        return new CustomUserDetails(user);
    }
}