package br.com.estud_io_api.utils;

import br.com.estud_io_api.enums.LanguageOption;

public class CustomizedMessageUtils {

    public static String getCustomMessage(LanguageOption languageOption, String msgEnglish , String msgPtBr){
        return languageOption == LanguageOption.ENGLISH ?
                msgEnglish:
                msgPtBr;
    }
}
