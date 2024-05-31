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

import com.example.tiendahigienemascotas.AdaptadorEspecialistas;
import com.example.tiendahigienemascotas.AdaptadorMascotas;
import com.example.tiendahigienemascotas.CallBacks.EspecialistasCallBack;
import com.example.tiendahigienemascotas.CallBacks.MascotasCallBack;
import com.example.tiendahigienemascotas.Controladores.ClienteController;
import com.example.tiendahigienemascotas.Controladores.EspecialistaController;
import com.example.tiendahigienemascotas.Controladores.MascotaController;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.EspecialistaDTO;
import com.example.tiendahigienemascotas.Modelos.MascotaDTO;
import com.example.tiendahigienemascotas.R;
import com.example.tiendahigienemascotas.Regex;

import java.util.List;

public class ModificarMascota extends AppCompatActivity implements MascotasCallBack, EspecialistasCallBack {
ListView listView_mascotas, listView_especialistas;
EditText nombre, edad;
AdaptadorMascotas adaptadorMascotas;
AdaptadorEspecialistas adaptadorEspecialistas;
MascotaDTO mascota, mascotaActualizada;
EspecialistaDTO especialista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_mascotas);

        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Inicializo los elementos
        listView_mascotas = findViewById(R.id.listView_modMascotas);
        listView_especialistas = findViewById(R.id.listView_modEspecialistas);
        nombre = findViewById(R.id.nombre_modMascota);
        edad = findViewById(R.id.edad_modMascota);

        //Realizo la petición para obtener todos los clientes y cargarlos en en listview
        MascotaController.getMascotas(this, this);

        //Realizo la petición para obtener todos los especialistas y cargarlos en en listview
        EspecialistaController.getEspecialistas(this, this);

        //Creo los listeners para obtener
        listView_mascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MascotaDTO mascotaDTO = (MascotaDTO) parent.getItemAtPosition(position);

                //Le establezco a los EditText los valores de la mascota para que el usuario cambie solo los que quiera
                nombre.setText(mascotaDTO.getNombre());
                edad.setText(Integer.toString(mascotaDTO.getEdad()));

                //Me guardo la mascota seleccionada
                mascota = mascotaDTO;
            }
        });

        listView_especialistas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EspecialistaDTO especialistaDTO = (EspecialistaDTO) parent.getItemAtPosition(position);

                //Me guardo el DNI del especialista seleccionado
                especialista = especialistaDTO;
            }
        });

    }

    @Override
    public void onSuccessMascotas(List<MascotaDTO> listaMascotasDTO) {
        if(listView_mascotas.getAdapter() == null) {
            adaptadorMascotas = new AdaptadorMascotas(this, listaMascotasDTO);
            listView_mascotas.setAdapter(adaptadorMascotas);
            adaptadorMascotas.notifyDataSetChanged();
        } else {
            adaptadorMascotas.actualizarMascotas(listaMascotasDTO);
        }
    }

    @Override
    public void onSuccessMascota(MascotaDTO mascotaDTO) {}

    @Override
    public void onSuccessModMascota(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessEspecialistas(List<EspecialistaDTO> listaEspecialistasDTO) {
        if(listView_especialistas.getAdapter() == null) {
            adaptadorEspecialistas = new AdaptadorEspecialistas(this, listaEspecialistasDTO);
            listView_especialistas.setAdapter(adaptadorEspecialistas);
            adaptadorEspecialistas.notifyDataSetChanged();
        } else {
            adaptadorEspecialistas.actualizarEspecialistas(listaEspecialistasDTO);
        }
    }

    @Override
    public void onSuccessEspecialista(EspecialistaDTO especialistaDTO) {}

    @Override
    public void onSuccessModEspecialista(String mensaje) {}

    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void modificarMascota(View view) {
        if(mascota == null || especialista == null) {
            Toast.makeText(this, "Debe seleccionar una mascota y un especialista", Toast.LENGTH_LONG).show();
        } else if(!Regex.datoEsEntero(edad.getText().toString())) {
            Toast.makeText(this, "Debe proporcionar una edad entera, con números", Toast.LENGTH_LONG).show();
        } else {
            //Creo una mascota para enviar los datos para modificar la seleccionada
            mascotaActualizada = new MascotaDTO(
                    mascota.getDNI(),
                    nombre.getText().toString(),
                    Integer.parseInt(edad.getText().toString()),
                    mascota.getSexo(),
                    mascota.getEspecie(),
                    mascota.getRaza(),
                    especialista.getDNI());

            //Llamo a la petición para modificar la mascota
            MascotaController.actualizarMascotaPorDNI(mascota.getDNI(), mascotaActualizada, this, this);
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