package br.com.estud_io_api.service;

import br.com.estud_io_api.UserDTO;
import br.com.estud_io_api.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService service = new AuthService();

    @DisplayName("TEST - Create Account")
    @Test()
    void createAccountTest(){
        UserDTO dto = new UserDTO("Test","test@gmail.com","123", null);
        User user = service.createAccount(dto, 2);
        assert user != null;
    }
}
