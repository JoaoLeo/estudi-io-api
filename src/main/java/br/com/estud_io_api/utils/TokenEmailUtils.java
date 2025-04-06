package br.com.estud_io_api.utils;

import java.util.UUID;

public class TokenEmailUtils {

    public static String generateEmailVerificationToken() {
        return UUID.randomUUID().toString();
    }
    
}
