package br.com.estud_io_api.utils;

import java.util.Locale;

public class LocaleUtils {

    public static Locale returnLocalFromHeader(Locale locale) {
        return (locale != null) ? locale : Locale.forLanguageTag("pt-BR");
    }

    public static Locale getLocaleByLanguageCode(String languageCode) {
        return (languageCode != null) ? Locale.forLanguageTag(languageCode) : Locale.forLanguageTag("pt-BR");
    }

}
