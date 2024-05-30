package com.example.tiendahigienemascotas.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.tiendahigienemascotas.CallBacks.ClientesCallBack;
import com.example.tiendahigienemascotas.CallBacks.LoginCallBack;
import com.example.tiendahigienemascotas.Controladores.ClienteController;
import com.example.tiendahigienemascotas.Controladores.CuentaController;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.Cuenta;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.example.tiendahigienemascotas.R;

import java.util.List;

public class ModificarCliente extends AppCompatActivity implements ClientesCallBack {
    ListView listview_clientes;
    AdaptadorClientes adaptador;
    EditText nombre, apellidos, telefono, correo, residencia;
    String DNI_seleccionado;
    Cliente clienteActualizado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_clientes);

        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Inicializo los elementos
        listview_clientes = findViewById(R.id.listView);

        nombre = findViewById(R.id.editText);
        apellidos = findViewById(R.id.editText2);
        telefono = findViewById(R.id.editText3);
        correo = findViewById(R.id.editText4);
        residencia = findViewById(R.id.editText5);

        //Realizo la petición para obtener todos los clientes y cargarlos en en listview
        ClienteController.getClientes(this, this);

        //Creo el listener para cargar el cliente seleccionado
        listview_clientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente = (Cliente) parent.getItemAtPosition(position);

                //Le establezco a los EditText los valores del cliente para que el usuario cambie solo los que quiera
                nombre.setText(cliente.getNombre());
                apellidos.setText(cliente.getApellidos());
                telefono.setText(cliente.getTelefono());
                correo.setText(cliente.getCorreo());
                residencia.setText(cliente.getResidencia());

                //Me guardo el DNI del cliente seleccionado
                DNI_seleccionado = cliente.getDNI();

            }
        });


    }

    @Override
    public void onSuccessClientes(List<Cliente> array_clientes) {
        if(listview_clientes.getAdapter() == null) {
            adaptador = new AdaptadorClientes(ModificarCliente.this, array_clientes);
            listview_clientes.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(Cliente cliente) {}

    @Override
    public void onSuccessModCliente(String mensaje) {
        //Si se ha modificado el cliente
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void modificarCliente(View view) {
        //Creo un cliente para enviar los datos para modificar el seleccionado
        clienteActualizado = new Cliente(
                DNI_seleccionado,
                nombre.getText().toString(),
                apellidos.getText().toString(),
                telefono.getText().toString(),
                correo.getText().toString(),
                residencia.getText().toString());

        //Llamo a la petición para modificar el cliente
        ClienteController.actualizarClientePorDNI(DNI_seleccionado, clienteActualizado, this, this);

    }

    @Override
    public void onBackPressed() {
        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        super.onBackPressed();
    }

}