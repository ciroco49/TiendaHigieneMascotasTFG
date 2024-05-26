package com.example.tiendahigienemascotas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    //Valido el correo
    public static boolean validarCorreo(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Valido el DNI
    public static boolean validarDNI(String dni) {
        String dniRegex = "^\\d{8}[A-Za-z]$";
        Pattern pattern = Pattern.compile(dniRegex);
        Matcher matcher = pattern.matcher(dni);
        return matcher.matches();
    }

}
