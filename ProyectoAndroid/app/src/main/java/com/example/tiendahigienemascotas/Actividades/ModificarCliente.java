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
import com.example.tiendahigienemascotas.Controladores.ClienteController;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.example.tiendahigienemascotas.R;
import com.example.tiendahigienemascotas.Regex;

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

        //Si el idioma seleccionado no coincide con el Locale que tiene esta actividad, recargo para setear el idioma bien
        if(!PreferenciasCompartidas.obtenerCodigoIdioma(ModificarCliente.this).equals(Ajustes.obtenerIdiomaActividad(ModificarCliente.this))) {
            Ajustes.establecerIdiomaActividad(ModificarCliente.this, PreferenciasCompartidas.obtenerCodigoIdioma(ModificarCliente.this));
            recreate();
            return;
        }

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
        if(!Regex.datoEsEntero(telefono.getText().toString()) || !Regex.datoTieneNueveCaracteres(telefono.getText().toString())) {
            Toast.makeText(this, "Debe proporcionar un teléfono entero, con 9 números", Toast.LENGTH_LONG).show();
            return;
        } else if(!Regex.validarCorreo(correo.getText().toString())) {
            Toast.makeText(this, "El correo proporcionado no cumple el formato requerido", Toast.LENGTH_LONG).show();
            return;
        }

        if(DNI_seleccionado != null) {
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
        } else {
            Toast.makeText(this, "Debe seleccionar un cliente", Toast.LENGTH_LONG).show();
        }

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