package com.abarrotescasavargas.operamovil.Main.FunGenerales;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpresionesRegulares {
    public static String URL(String urlString) {
        String regex = "/\\d+(/.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(urlString);
        if (matcher.find()) {
            String result = matcher.group();
            return result;
        } else {
            return "No se encontró ninguna coincidencia.";
        }

    }
}
