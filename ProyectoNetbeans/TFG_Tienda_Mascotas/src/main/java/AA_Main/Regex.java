package AA_Main;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ciroi
 */
public class Regex {
    
    //Valido el correo
    public static boolean validarCorreo(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern patron = Pattern.compile(emailRegex);
        Matcher matcher = patron.matcher(email);
        return matcher.matches();
    }

    //Valido el DNI
    public static boolean validarDNI(String dni) {
        String dniRegex = "^\\d{8}[A-Za-z]$";
        Pattern patron = Pattern.compile(dniRegex);
        Matcher matcher = patron.matcher(dni);
        return matcher.matches();
    }
    
    
    public static boolean datoEsEntero(String dato) {
        //Intento parsear el dato para comprobar si es un entero o no
        try {
            Integer.parseInt(dato);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean datoEsDouble(String dato) {
        //Intento parsear el dato para comprobar si es un double o no
        try {
            Double.parseDouble(dato);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
}
