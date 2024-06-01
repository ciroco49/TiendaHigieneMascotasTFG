package com.example.tiendahigienemascotas.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendahigienemascotas.AdaptadorClientes;
import com.example.tiendahigienemascotas.CallBacks.ClientesCallBack;
import com.example.tiendahigienemascotas.CallBacks.LoginCallBack;
import com.example.tiendahigienemascotas.Controladores.ClienteController;
import com.example.tiendahigienemascotas.Controladores.CuentaController;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.Cuenta;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.example.tiendahigienemascotas.R;
import com.example.tiendahigienemascotas.Regex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsultarClientes extends AppCompatActivity implements ClientesCallBack {
ListView listview_clientes;
AdaptadorClientes adaptador;
EditText filtroDNI_cliente;
EditText filtroNombre_cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultar_clientes);

        //Si el idioma seleccionado no coincide con el Locale que tiene esta actividad, recargo para setear el idioma bien
        if(!PreferenciasCompartidas.obtenerCodigoIdioma(ConsultarClientes.this).equals(Ajustes.obtenerIdiomaActividad(ConsultarClientes.this))) {
            Ajustes.establecerIdiomaActividad(ConsultarClientes.this, PreferenciasCompartidas.obtenerCodigoIdioma(ConsultarClientes.this));
            recreate();
            return;
        }

        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Inicializo los elementos de mi layout
        listview_clientes = findViewById(R.id.listView_consultarClientes);
        filtroDNI_cliente = findViewById(R.id.filtroDNICliente);
        filtroNombre_cliente = findViewById(R.id.filtroNombreCliente);

        //Al entrar a esta pantalla, cargo todos los clientes
        ClienteController.getClientes(this, this);

        //Creo el listener para detectar clicks en clientes de la lista
        listview_clientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente_seleccionado = (Cliente) parent.getItemAtPosition(position);

                //Voy a la pantalla de info, enviando el DNI del cliente seleccionado para obtenerlo con una petición
                Intent infoCliente = new Intent(ConsultarClientes.this, InfoCliente.class);
                infoCliente.putExtra("DNI", cliente_seleccionado.getDNI());
                startActivity(infoCliente);
            }
        });


    }

    @Override
    public void onSuccessClientes(List<Cliente> array_clientes) {
        if(listview_clientes.getAdapter() == null) {
            adaptador = new AdaptadorClientes(this, array_clientes);
            listview_clientes.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        } else {
            adaptador.actualizarClientes(array_clientes);
        }
    }

    @Override
    public void onSuccess(Cliente cliente) {
        adaptador.actualizarCliente(cliente);
    }

    @Override
    public void onSuccessModCliente(String mensaje) {}

    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void filtrarClientesDNI(View view) {
        String DNI = filtroDNI_cliente.getText().toString();
        if(DNI.isEmpty()) {
            Toast.makeText(this, "Es obligatorio proporcionar un DNI", Toast.LENGTH_LONG).show();
        } else if (!Regex.validarDNI(DNI)) {
            Toast.makeText(this, "El DNI proporcionado no cumple el formato requerido", Toast.LENGTH_LONG).show();
        }  else {
            ClienteController.getClientePorDNI(DNI, this, this);
        }

    }

    public void filtrarClientesNombre(View view) {
        String nombre = filtroNombre_cliente.getText().toString();
        if(nombre.isEmpty()) {
            Toast.makeText(this, "Es obligatorio proporcionar un nombre", Toast.LENGTH_LONG).show();
        } else if (nombre.length() > 50) {
            Toast.makeText(this, "El nombre proporcionado no puede tener más de 50 caracteres", Toast.LENGTH_LONG).show();
        } else {
            ClienteController.getClientesPorNombre(nombre, this, this);
        }
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