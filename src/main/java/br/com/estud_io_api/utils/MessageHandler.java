package br.com.estud_io_api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageHandler {

    @Autowired
    private MessageSource messageSource;

    public String getCustomMessage(Locale locale, String message) {
        try {
            return messageSource.getMessage(message, null, locale);
        } catch (NoSuchMessageException e) {
            return "Message not found for key: " + message;
        }
    }

    public String getCustomMessageWithParams(Locale locale, String message, String param) {
        try {
            return messageSource.getMessage(message, new Object[]{param}, locale);
        } catch (NoSuchMessageException e) {
            return "Message not found for key: " + message;
        }
    }
}
