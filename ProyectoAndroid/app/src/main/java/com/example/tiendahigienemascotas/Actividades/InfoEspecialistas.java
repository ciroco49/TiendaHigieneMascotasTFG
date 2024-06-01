package com.example.tiendahigienemascotas.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendahigienemascotas.AdaptadorMascotas;
import com.example.tiendahigienemascotas.CallBacks.EspecialistasCallBack;
import com.example.tiendahigienemascotas.CallBacks.MascotasCallBack;
import com.example.tiendahigienemascotas.Controladores.ClienteController;
import com.example.tiendahigienemascotas.Controladores.EspecialistaController;
import com.example.tiendahigienemascotas.Controladores.MascotaController;
import com.example.tiendahigienemascotas.Modelos.EspecialistaDTO;
import com.example.tiendahigienemascotas.Modelos.MascotaDTO;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.example.tiendahigienemascotas.R;

import java.util.List;

public class InfoEspecialistas extends AppCompatActivity implements EspecialistasCallBack {
    ListView listView_mascotas;
    AdaptadorMascotas adaptador;
    TextView dni, nombre, apellidos, telefono, correo, residencia, sueldo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_especialistas);

        //Si el idioma seleccionado no coincide con el Locale que tiene esta actividad, recargo para setear el idioma bien
        if(!PreferenciasCompartidas.obtenerCodigoIdioma(InfoEspecialistas.this).equals(Ajustes.obtenerIdiomaActividad(InfoEspecialistas.this))) {
            Ajustes.establecerIdiomaActividad(InfoEspecialistas.this, PreferenciasCompartidas.obtenerCodigoIdioma(InfoEspecialistas.this));
            recreate();
            return;
        }

        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        String DNI = getIntent().getStringExtra("DNI");

        //Inicializo los elementos de mi layout
        dni = findViewById(R.id.dni_infoEspecialistas);
        nombre = findViewById(R.id.nombre_infoEspecialistas);
        apellidos = findViewById(R.id.apellidos_infoEspecialistas);
        telefono = findViewById(R.id.telefono_infoEspecialistas);
        correo = findViewById(R.id.correo_infoEspecialistas);
        residencia = findViewById(R.id.residencia_infoEspecialistas);
        sueldo = findViewById(R.id.sueldo_infoEspecialistas);

        listView_mascotas = findViewById(R.id.listView_infoEspecialistas);

        //Realizo la petición para obtener el especialista con dicho DNI
        EspecialistaController.getEspecialistaPorDNI(DNI, this, this);

    }

    @Override
    public void onBackPressed() {
        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Si no se realizó el intent porque sí existe la cuenta loggeada
        super.onBackPressed();
    }

    @Override
    public void onSuccessEspecialistas(List<EspecialistaDTO> listaEspecialistasDTO) {}

    @Override
    public void onSuccessEspecialista(EspecialistaDTO especialistaDTO) {
        //Si ha devuelto un cliente exitosamente, cargo su información
        dni.setText(especialistaDTO.getDNI());
        nombre.setText(especialistaDTO.getNombre());
        apellidos.setText(especialistaDTO.getApellidos());
        telefono.setText(especialistaDTO.getTelefono());
        correo.setText(especialistaDTO.getCorreo());
        residencia.setText(especialistaDTO.getResidencia());
        sueldo.setText(Double.toString(especialistaDTO.getSueldo()));

        //Realizo la petición para obtener las mascotas del especialista
        MascotaController.getMascotasPorEspecialista(especialistaDTO.getDNI(), this, new MascotasCallBack() {
            @Override
            public void onSuccessMascotas(List<MascotaDTO> listaMascotasDTO) {
                if(listView_mascotas.getAdapter() == null) {
                    adaptador = new AdaptadorMascotas(InfoEspecialistas.this, listaMascotasDTO);
                    listView_mascotas.setAdapter(adaptador);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onSuccessMascota(MascotaDTO mascotaDTO) {}

            @Override
            public void onSuccessModMascota(String mensaje) {}

            @Override
            public void onError(String mensaje) {
                Toast.makeText(InfoEspecialistas.this, mensaje, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onSuccessModEspecialista(String mensaje) {}

    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void ruedaAjustes(View view) {
        Intent ajustes = new Intent(this, Ajustes.class);
        startActivity(ajustes);
    }
}