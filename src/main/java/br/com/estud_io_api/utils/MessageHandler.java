package br.com.estud_io_api.utils;

import br.com.estud_io_api.enums.LanguageOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageHandler {

    @Autowired
    private MessageSource messageSource;

    public String getCustomMessage(int languageOption, String msgEnglish , String msgPtBr) {
        LanguageOption lang = LanguageOption.fromValue(languageOption);
        String option = lang == LanguageOption.ENGLISH ?
                msgEnglish:
                msgPtBr;
        return messageSource.getMessage(option,null, Locale.getDefault());
    }
}
