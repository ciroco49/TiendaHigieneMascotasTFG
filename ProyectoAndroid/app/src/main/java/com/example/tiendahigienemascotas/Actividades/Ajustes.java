package com.example.tiendahigienemascotas.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendahigienemascotas.CallBacks.LoginCallBack;
import com.example.tiendahigienemascotas.Controladores.CuentaController;
import com.example.tiendahigienemascotas.Modelos.Cuenta;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.example.tiendahigienemascotas.R;
import com.example.tiendahigienemascotas.Regex;

public class Ajustes extends AppCompatActivity {
EditText IP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_ajustes);

        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        IP = findViewById(R.id.IP_ajustes);

    }

    public void btnCerrarSesion(View view) {
        PreferenciasCompartidas.limpiarPreferenciasCompartidasLogin(this);
        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }

    @Override
    public void onBackPressed() {
        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        super.onBackPressed();
    }

    public void aplicarCambios(View view) {
        String ipv4 = IP.getText().toString();
        //Si hay alguna IP escrita que cumpla el patrón requerido la guardo
        if(!ipv4.isEmpty() && Regex.validarIP(ipv4)) {
            PreferenciasCompartidas.guardarIP(this, ipv4);
        }
    }

}