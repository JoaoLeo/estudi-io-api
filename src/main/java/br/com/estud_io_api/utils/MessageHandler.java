package br.com.estud_io_api.utils;

import br.com.estud_io_api.enums.LanguageOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageHandler {

    @Autowired
    private MessageSource messageSource;

    public String getCustomMessage(int languageOption, String message) {
        LanguageOption lang = LanguageOption.fromValue(languageOption);
        Locale locale = lang == LanguageOption.ENGLISH ?
                Locale.ENGLISH : new Locale("pt", "BR");
        try {
            return messageSource.getMessage(message, null, locale);
        } catch (NoSuchMessageException e) {
            return "Message not found for key: " + message;
        }
    }
}
