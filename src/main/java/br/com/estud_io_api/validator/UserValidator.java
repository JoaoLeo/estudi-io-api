package br.com.estud_io_api.validator;

import br.com.estud_io_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    @Autowired
    private UserRepository userRepo;

    public boolean emailInUse(String email){
        return userRepo.existsByEmail(email);
    }
}
