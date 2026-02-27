package br.com.estud_io_api.controller;

import br.com.estud_io_api.utils.LocaleUtils;
import br.com.estud_io_api.utils.MessageHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("public")
public class DefaultController {

    private final MessageHandler messageHandler;

    public DefaultController(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @GetMapping("hello-world")
    public ResponseEntity<String> getDefaultEndpoint(@RequestHeader(name = "Accept-Language", required = false)
                                                         Locale locale) {
        return ResponseEntity.ok(messageHandler.getCustomMessage(
                LocaleUtils.returnLocalFromHeader(locale),
                "hello.world"));
    }
}
