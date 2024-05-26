package com.example.tiendahigienemascotas.Actividades;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendahigienemascotas.AdaptadorMascotas;
import com.example.tiendahigienemascotas.CallBacks.ClientesCallBack;
import com.example.tiendahigienemascotas.CallBacks.TenerCallBack;
import com.example.tiendahigienemascotas.Controladores.ClienteController;
import com.example.tiendahigienemascotas.Controladores.TenerController;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.MascotaDTO;
import com.example.tiendahigienemascotas.Modelos.TenerDTO;
import com.example.tiendahigienemascotas.R;

import java.util.ArrayList;
import java.util.List;

public class InfoCliente extends AppCompatActivity implements ClientesCallBack, TenerCallBack {
TextView dni, nombre, apellidos, telefono, correo, residencia;
ListView listView_Mascotas;
AdaptadorMascotas adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_clientes);

        String DNI = getIntent().getStringExtra("DNI");

        //Inicializo los elementos de mi layout
        dni = findViewById(R.id.dni_infocliente);
        nombre = findViewById(R.id.nombre_infocliente);
        apellidos = findViewById(R.id.apellidos_infocliente);
        telefono = findViewById(R.id.telefono_infocliente);
        correo = findViewById(R.id.correo_infocliente);
        residencia = findViewById(R.id.residencia_infocliente);

        listView_Mascotas = findViewById(R.id.listView_mascotas);


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

//        this.cliente.setDNI(cliente.getDNI());
//        this.cliente.setNombre(cliente.getNombre());
//        this.cliente.setApellidos(cliente.getApellidos());
//        this.cliente.setTelefono(cliente.getTelefono());
//        this.cliente.setCorreo(cliente.getCorreo());
//        this.cliente.setResidencia(cliente.getResidencia());

        //Realizo la petición para obtener las mascotas del cliente
        TenerController.getTenerPorDNI_Cliente(cliente, this, this);

    }


    @Override
    public void onSuccess(List<TenerDTO> listaTener) {
        List<MascotaDTO> listMascotas = new ArrayList<>();

        for(TenerDTO tener: listaTener) {
            listMascotas.add(tener.getDNIMascota());
        }

        if(listView_Mascotas.getAdapter() == null) {
            adaptador = new AdaptadorMascotas(this, listMascotas);
            listView_Mascotas.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }

    }

    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }



}
