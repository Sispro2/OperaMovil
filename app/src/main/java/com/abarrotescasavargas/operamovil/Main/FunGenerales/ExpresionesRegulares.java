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
    public static String fecha(String fechaHoraOriginal)
    {
        //Extrae la fecha en formato "yyyy-MM-dd" de una cadena de fecha y hora
        //proporcionada en el parámetro de entrada y devuelve la fecha extraída,
        //o null si no se encontró una fecha válida en la cadena.
        String patronFecha = "(\\d{4}-\\d{2}-\\d{2})";
        Pattern patron = Pattern.compile(patronFecha);
        Matcher matcher = patron.matcher(fechaHoraOriginal);
        if (matcher.find()) {
            String fechaExtraida = matcher.group(1);
            return fechaExtraida;
        } else {
            return null;
        }
    }
}
