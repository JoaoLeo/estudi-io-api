package br.com.estud_io_api.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Locale;
import java.util.UUID;

@Component
public class TokenEmailUtils {

    @Autowired
    private HttpServletRequest request;

    public String generateEmailVerificationToken() {
        return UUID.randomUUID().toString();
    }

    public String generateLink(String token, Locale locale) {
        String contextPath = request.getContextPath();
        String link = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(contextPath + "/auth/verify-account")
                .queryParam("token", token)
                .queryParam("languageOption", locale.getLanguage())
                .toUriString();
        return link;
    }
    
}
