package com.example.tiendahigienemascotas.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.example.tiendahigienemascotas.R;

public class Ajustes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_ajustes);



    }

    public void btnCerrarSesion(View view) {
        PreferenciasCompartidas.limpiarPreferenciasCompartidas(this);
        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }
}