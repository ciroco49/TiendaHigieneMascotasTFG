package com.example.tiendahigienemascotas.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendahigienemascotas.AdaptadorClientes;
import com.example.tiendahigienemascotas.AdaptadorMascotas;
import com.example.tiendahigienemascotas.CallBacks.MascotasCallBack;
import com.example.tiendahigienemascotas.Controladores.ClienteController;
import com.example.tiendahigienemascotas.Controladores.MascotaController;
import com.example.tiendahigienemascotas.Modelos.MascotaDTO;
import com.example.tiendahigienemascotas.R;
import com.example.tiendahigienemascotas.Regex;

import java.util.List;

public class ConsultarMascotas extends AppCompatActivity implements MascotasCallBack {
    ListView listview_mascotas;
    AdaptadorMascotas adaptador;
    EditText filtro_DNI, filtro_especie, filtro_raza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar_mascotas);

        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Inicializo los elementos de mi layout
        listview_mascotas = findViewById(R.id.listView_consultarMascotas);
        filtro_DNI = findViewById(R.id.filtroDNIMascota);
        filtro_especie = findViewById(R.id.filtroEspecieMascota);
        filtro_raza = findViewById(R.id.filtroRazaMascota);

        //Al entrar a esta pantalla, cargo todas las mascotas
        MascotaController.getMascotas(this, this);

        listview_mascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MascotaDTO mascotaDTO = (MascotaDTO) parent.getItemAtPosition(position);

                Intent infoMascota = new Intent(ConsultarMascotas.this, InfoMascota.class);
                infoMascota.putExtra("DNI", mascotaDTO.getDNI());
                startActivity(infoMascota);

            }
        });

    }

    @Override
    public void onSuccessMascotas(List<MascotaDTO> listaMascotasDTO) {
        if(listview_mascotas.getAdapter() == null) {
            adaptador = new AdaptadorMascotas(this, listaMascotasDTO);
            listview_mascotas.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        } else {
            adaptador.actualizarMascotas(listaMascotasDTO);
        }
    }

    @Override
    public void onSuccessMascota(MascotaDTO mascotaDTO) {
        adaptador.actualizarMascota(mascotaDTO);
    }

    @Override
    public void onSuccessModMascota(String mensaje) {}

    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void filtrarMascotasDNI(View view) {
        String DNI = filtro_DNI.getText().toString();
        if(DNI.isEmpty()) {
            Toast.makeText(this, "Es obligatorio proporcionar un DNI", Toast.LENGTH_LONG).show();
        } else if (!Regex.validarDNI(DNI)) {
            Toast.makeText(this, "El DNI proporcionado no cumple el formato requerido", Toast.LENGTH_LONG).show();
        }  else {
            MascotaController.getMascotaPorDNI(DNI, this, this);
        }
    }

    public void filtrarMascotasEspecie(View view) {
        String especie = filtro_especie.getText().toString();
        MascotaController.getMascotasPorEspecie(especie, this, this);
    }

    public void filtrarMascotasRaza(View view) {
        String raza = filtro_raza.getText().toString();
        MascotaController.getMascotasPorRaza(raza, this, this);
    }

    @Override
    public void onBackPressed() {
        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Si no se realizó el intent porque sí existe la cuenta loggeada
        super.onBackPressed();
    }

    public void ruedaAjustes(View view) {
        Intent ajustes = new Intent(this, Ajustes.class);
        startActivity(ajustes);
    }
}