package br.com.estud_io_api.service;

import br.com.estud_io_api.dto.user.UserDTO;
import br.com.estud_io_api.entity.auth.User;
import br.com.estud_io_api.service.auth.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService service = new AuthService();

    @DisplayName("TEST - Create Account")
    @Test()
    void createAccountTest(){
        UserDTO dto = new UserDTO("Test","test@gmail.com","123");
        User user = service.createAccount(dto, Locale.ENGLISH);
        assert user != null;
    }
}
