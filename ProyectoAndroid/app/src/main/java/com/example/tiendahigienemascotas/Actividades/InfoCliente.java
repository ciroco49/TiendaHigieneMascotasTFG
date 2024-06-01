package com.example.tiendahigienemascotas.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendahigienemascotas.AdaptadorMascotas;
import com.example.tiendahigienemascotas.CallBacks.ClientesCallBack;
import com.example.tiendahigienemascotas.CallBacks.LoginCallBack;
import com.example.tiendahigienemascotas.CallBacks.TenerCallBack;
import com.example.tiendahigienemascotas.Controladores.ClienteController;
import com.example.tiendahigienemascotas.Controladores.CuentaController;
import com.example.tiendahigienemascotas.Controladores.TenerController;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.Cuenta;
import com.example.tiendahigienemascotas.Modelos.MascotaDTO;
import com.example.tiendahigienemascotas.Modelos.TenerDTO;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.example.tiendahigienemascotas.R;

import java.util.ArrayList;
import java.util.List;

public class InfoCliente extends AppCompatActivity implements ClientesCallBack {
TextView dni, nombre, apellidos, telefono, correo, residencia;
ListView listView_mascotas;
AdaptadorMascotas adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_clientes);

        //Si el idioma seleccionado no coincide con el Locale que tiene esta actividad, recargo para setear el idioma bien
        if(!PreferenciasCompartidas.obtenerCodigoIdioma(InfoCliente.this).equals(Ajustes.obtenerIdiomaActividad(InfoCliente.this))) {
            Ajustes.establecerIdiomaActividad(InfoCliente.this, PreferenciasCompartidas.obtenerCodigoIdioma(InfoCliente.this));
            recreate();
            return;
        }

        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        String DNI = getIntent().getStringExtra("DNI");

        //Inicializo los elementos de mi layout
        dni = findViewById(R.id.dni_infocliente);
        nombre = findViewById(R.id.nombre_infocliente);
        apellidos = findViewById(R.id.apellidos_infocliente);
        telefono = findViewById(R.id.telefono_infocliente);
        correo = findViewById(R.id.correo_infocliente);
        residencia = findViewById(R.id.residencia_infocliente);

        listView_mascotas = findViewById(R.id.listView_mascotas);


        //Realizo la petición para obtener el cliente con dicho DNI
        ClienteController.getClientePorDNI(DNI, this, this);


    }


    @Override
    public void onSuccessClientes(List<Cliente> array_clientes) {}

    @Override
    public void onSuccess(Cliente cliente) {
        //Si ha devuelto un cliente exitosamente, cargo su información
        dni.setText(cliente.getDNI());
        nombre.setText(cliente.getNombre());
        apellidos.setText(cliente.getApellidos());
        telefono.setText(cliente.getTelefono());
        correo.setText(cliente.getCorreo());
        residencia.setText(cliente.getResidencia());

        //Realizo la petición para obtener las mascotas del cliente
        TenerController.getTenerPorDNI_Cliente(cliente, InfoCliente.this, new TenerCallBack() {
            @Override
            public void onSuccess(List<TenerDTO> listaTener) {
                List<MascotaDTO> listMascotas = new ArrayList<>();

                for(TenerDTO tener: listaTener) {
                    listMascotas.add(tener.getMascota());
                }

                if(listView_mascotas.getAdapter() == null) {
                    adaptador = new AdaptadorMascotas(InfoCliente.this, listMascotas);
                    listView_mascotas.setAdapter(adaptador);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String mensaje) {
                Toast.makeText(InfoCliente.this, mensaje, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onSuccessModCliente(String mensaje) {}

    @Override
    public void onError(String mensaje) {
        Toast.makeText(InfoCliente.this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void ruedaAjustes(View view) {
        Intent ajustes = new Intent(this, Ajustes.class);
        startActivity(ajustes);
    }

    @Override
    public void onBackPressed() {
        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        super.onBackPressed();
    }

}



