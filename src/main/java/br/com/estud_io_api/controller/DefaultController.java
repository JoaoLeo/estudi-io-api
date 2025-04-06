package br.com.estud_io_api.controller;

import br.com.estud_io_api.utils.MessageHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class DefaultController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MessageHandler messageHandler;

    @GetMapping("hello-world")
    public ResponseEntity<String> getDefaultEndpoint(){
        int language = request.getIntHeader("LanguageOption");
        return ResponseEntity.ok(messageHandler.getCustomMessage(language,
                "hello-world"));
    }
}
