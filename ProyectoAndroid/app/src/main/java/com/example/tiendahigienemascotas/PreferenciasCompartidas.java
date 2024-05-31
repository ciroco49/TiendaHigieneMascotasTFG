package com.example.tiendahigienemascotas;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PreferenciasCompartidas {

    private static final String preferencias = "preferencias";
    private static final String correo_encriptado = "correo_encriptado";
    private static final String ip = "IP";
    private static final String clave_secreta = "clavesecreta_123";

    public static void guardarCorreoEncriptado(Context contexto, String correo) {
        try {
            String correoEncriptado = encriptar(correo, clave_secreta);
            SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(correo_encriptado, correoEncriptado);
            editor.apply();
        } catch (GeneralSecurityException e) {
            Log.e("PreferenciasCompartidas", "Error al encriptar el correo: " + e.getMessage());
        }
    }

    public static String obtenerCorreoDesencriptado(Context contexto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        String correoEncriptado = sharedPreferences.getString(correo_encriptado, null);
        if (correoEncriptado != null) {
            try {
                return desencriptar(correoEncriptado, clave_secreta);
            } catch (GeneralSecurityException e) {
                Log.e("PreferenciasCompartidas", "Error al desencriptar el correo: " + e.getMessage());
            }
        }
        return null;
    }

    public static void guardarIP(Context contexto, String IP) {
            SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ip, IP);
            editor.apply();
    }

    public static String obtenerIP(Context contexto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, Context.MODE_PRIVATE);
        String IP = sharedPreferences.getString("IP", null);
        return IP;
    }

    private static String encriptar(String texto, String clave) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(clave.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] bytesEncriptados = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeToString(bytesEncriptados, Base64.DEFAULT);
    }

    private static String desencriptar(String textoEncriptado, String clave) throws GeneralSecurityException {
        byte[] bytesEncriptados = Base64.decode(textoEncriptado, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(clave.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] bytesDesencriptados = cipher.doFinal(bytesEncriptados);
        return new String(bytesDesencriptados, StandardCharsets.UTF_8);
    }

    public static void limpiarPreferenciasCompartidasLogin(Context contexto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, contexto.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(correo_encriptado).apply();
    }

    public static void limpiarPreferenciasCompartidasIP(Context contexto) {
        SharedPreferences sharedPreferences = contexto.getSharedPreferences(preferencias, contexto.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ip).apply();
    }

}
