package com.example.tiendahigienemascotas.Actividades;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.tiendahigienemascotas.AdaptadorEspecialistas;
import com.example.tiendahigienemascotas.AdaptadorMascotas;
import com.example.tiendahigienemascotas.CallBacks.EspecialistasCallBack;
import com.example.tiendahigienemascotas.Controladores.ClienteController;
import com.example.tiendahigienemascotas.Controladores.EspecialistaController;
import com.example.tiendahigienemascotas.Controladores.MascotaController;
import com.example.tiendahigienemascotas.Modelos.EspecialistaDTO;
import com.example.tiendahigienemascotas.R;
import com.example.tiendahigienemascotas.Regex;

import java.util.List;

public class ConsultarEspecialistas extends AppCompatActivity implements EspecialistasCallBack {
    ListView listview_especialistas;
    AdaptadorEspecialistas adaptador;
    EditText filtroDNI, filtroNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar_especialistas);

        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Inicializo los elementos de mi layout
        listview_especialistas = findViewById(R.id.listView_consultarEspecialista);

        filtroDNI = findViewById(R.id.filtroDNIEspecialista);
        filtroNombre = findViewById(R.id.filtroNombreEspecialista);

        //Al entrar a esta pantalla, cargo todos los especialistas
        EspecialistaController.getEspecialistas(this, this);

        listview_especialistas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EspecialistaDTO especialistaDTO = (EspecialistaDTO) parent.getItemAtPosition(position);

                Intent infoEspecialista = new Intent(ConsultarEspecialistas.this, InfoEspecialistas.class);
                infoEspecialista.putExtra("DNI", especialistaDTO.getDNI());
                startActivity(infoEspecialista);
            }
        });

    }

    @Override
    public void onSuccessEspecialistas(List<EspecialistaDTO> listaEspecialistasDTO) {
        if(listview_especialistas.getAdapter() == null) {
            adaptador = new AdaptadorEspecialistas(this, listaEspecialistasDTO);
            listview_especialistas.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        } else {
            adaptador.actualizarEspecialistas(listaEspecialistasDTO);
        }
    }

    @Override
    public void onSuccessEspecialista(EspecialistaDTO especialistaDTO) {
        adaptador.actualizarEspecialista(especialistaDTO);
    }

    @Override
    public void onSuccessModEspecialista(String mensaje) {}

    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void filtrarEspecialistasDNI(View view) {
        String DNI = filtroDNI.getText().toString();

        if(DNI.isEmpty()) {
            Toast.makeText(this, "Es obligatorio proporcionar un DNI", Toast.LENGTH_LONG).show();
        } else if (!Regex.validarDNI(DNI)) {
            Toast.makeText(this, "El DNI proporcionado no cumple el formato requerido", Toast.LENGTH_LONG).show();
        }  else {
            EspecialistaController.getEspecialistaPorDNI(DNI, this, this);
        }
    }

    public void filtrarEspecialistasNombre(View view) {
        String nombre = filtroNombre.getText().toString();

        EspecialistaController.getEspecialistasPorNombre(nombre, this, this);
    }

    public void ruedaAjustes(View view) {
        Intent ajustes = new Intent(this, Ajustes.class);
        startActivity(ajustes);
    }

    @Override
    public void onBackPressed() {
        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Si no se realizó el intent porque sí existe la cuenta loggeada
        super.onBackPressed();
    }
}