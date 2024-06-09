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
import com.example.tiendahigienemascotas.CallBacks.EspecialistasCallBack;
import com.example.tiendahigienemascotas.Controladores.ClienteController;
import com.example.tiendahigienemascotas.Controladores.EspecialistaController;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.EspecialistaDTO;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.example.tiendahigienemascotas.R;
import com.example.tiendahigienemascotas.Regex;

import java.util.List;

public class ModificarEspecialista extends AppCompatActivity implements EspecialistasCallBack {
EditText nombre, apellidos, telefono, correo, residencia, sueldo;
ListView listView_especialistas;
AdaptadorEspecialistas adaptador;
EspecialistaDTO especialista_seleccionado, especialistaDTO_actualizado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_especialistas);

        //Si el idioma seleccionado no coincide con el Locale que tiene esta actividad, recargo para setear el idioma bien
        if(!PreferenciasCompartidas.obtenerCodigoIdioma(ModificarEspecialista.this).equals(Ajustes.obtenerIdiomaActividad(ModificarEspecialista.this))) {
            Ajustes.establecerIdiomaActividad(ModificarEspecialista.this, PreferenciasCompartidas.obtenerCodigoIdioma(ModificarEspecialista.this));
            recreate();
            return;
        }

        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Inicializo los elementos
        listView_especialistas = findViewById(R.id.listView_modEspecialistas);

        nombre = findViewById(R.id.nombre_modEspecialista);
        apellidos = findViewById(R.id.apellidos_modEspecialista);
        telefono = findViewById(R.id.telefono_modEspecialista);
        correo = findViewById(R.id.correo_modEspecialista);
        residencia = findViewById(R.id.residencia_modEspecialista);
        sueldo = findViewById(R.id.sueldo_modEspecialista);

        //Realizo la petición para obtener todos los especialistas y cargarlos en en listview
        EspecialistaController.getEspecialistas(this, this);

        //Creo el listener para cargar el especialista seleccionado
        listView_especialistas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EspecialistaDTO especialistaDTO = (EspecialistaDTO) parent.getItemAtPosition(position);

                //Le establezco a los EditText los valores del especialista para que el usuario cambie solo los que quiera
                nombre.setText(especialistaDTO.getNombre());
                apellidos.setText(especialistaDTO.getApellidos());
                telefono.setText(especialistaDTO.getTelefono());
                correo.setText(especialistaDTO.getCorreo());
                residencia.setText(especialistaDTO.getResidencia());
                sueldo.setText(Double.toString(especialistaDTO.getSueldo()));

                //Me guardo el especialista seleccionado
                especialista_seleccionado = especialistaDTO;

            }
        });


    }

    @Override
    public void onBackPressed() {
        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Si no se realizó el intent porque sí existe la cuenta loggeada
        super.onBackPressed();
    }

    @Override
    public void onSuccessEspecialistas(List<EspecialistaDTO> listaEspecialistasDTO) {
        if(listView_especialistas.getAdapter() == null) {
            adaptador = new AdaptadorEspecialistas(this, listaEspecialistasDTO);
            listView_especialistas.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccessEspecialista(EspecialistaDTO especialistaDTO) {

    }

    @Override
    public void onSuccessModEspecialista(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void modificarEspecialista(View view) {
        if(!Regex.datoEsEntero(telefono.getText().toString()) || !Regex.datoTieneNueveCaracteres(telefono.getText().toString())) {
            Toast.makeText(this, "Debe proporcionar un teléfono entero, con 9 números", Toast.LENGTH_LONG).show();
        } else if(!Regex.validarCorreo(correo.getText().toString())) {
            Toast.makeText(this, "El correo proporcionado no cumple el formato requerido", Toast.LENGTH_LONG).show();
        } else if(!Regex.datoEsDouble(sueldo.getText().toString())) {
            Toast.makeText(this, "Debe proporcionar un sueldo decimal, con números", Toast.LENGTH_LONG).show();
        }


        if(especialista_seleccionado != null) {
            //Creo un cliente para enviar los datos para modificar el seleccionado
            especialistaDTO_actualizado = new EspecialistaDTO(
                    especialista_seleccionado.getDNI(),
                    nombre.getText().toString(),
                    apellidos.getText().toString(),
                    telefono.getText().toString(),
                    correo.getText().toString(),
                    residencia.getText().toString(),
                    Double.parseDouble(sueldo.getText().toString()),
                    especialista_seleccionado.getdnimascotaList());

            //Llamo a la petición para modificar el especialista
            EspecialistaController.actualizarEspecialistaPorDNI(especialista_seleccionado.getDNI(), especialistaDTO_actualizado,
                                                                this, this);
        } else {
            Toast.makeText(this, "Debe seleccionar un especialista", Toast.LENGTH_LONG).show();
        }

    }

    public void ruedaAjustes(View view) {
        Intent ajustes = new Intent(this, Ajustes.class);
        startActivity(ajustes);
    }
}