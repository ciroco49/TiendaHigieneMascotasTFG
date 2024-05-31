package com.example.tiendahigienemascotas.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendahigienemascotas.AdaptadorClientes;
import com.example.tiendahigienemascotas.CallBacks.MascotasCallBack;
import com.example.tiendahigienemascotas.CallBacks.TenerCallBack;
import com.example.tiendahigienemascotas.Controladores.ClienteController;
import com.example.tiendahigienemascotas.Controladores.MascotaController;
import com.example.tiendahigienemascotas.Controladores.TenerController;
import com.example.tiendahigienemascotas.Modelos.Cliente;
import com.example.tiendahigienemascotas.Modelos.MascotaDTO;
import com.example.tiendahigienemascotas.Modelos.TenerDTO;
import com.example.tiendahigienemascotas.R;

import java.util.ArrayList;
import java.util.List;

public class InfoMascota extends AppCompatActivity implements MascotasCallBack, TenerCallBack {
TextView dni, nombre, edad, sexo, especie, raza, dni_especialista;
ListView listView_clientes;
AdaptadorClientes adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_mascotas);

        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        String DNI = getIntent().getStringExtra("DNI");

        //Inicializo los elementos de mi layout
        dni = findViewById(R.id.dni_infomascotas);
        nombre = findViewById(R.id.nombre_infomascotas);
        edad = findViewById(R.id.edad_infomascotas);
        sexo = findViewById(R.id.sexo_infomascotas);
        especie = findViewById(R.id.especie_infomascotas);
        raza = findViewById(R.id.raza_infomascotas);
        dni_especialista = findViewById(R.id.dniespecialista_infomascotas);

        listView_clientes = findViewById(R.id.listView_clientes);

        //Realizo la petición para obtener el cliente con dicho DNI
        MascotaController.getMascotaPorDNI(DNI, this, this);

    }

    @Override
    public void onBackPressed() {
        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Si no se realizó el intent porque sí existe la cuenta loggeada
        super.onBackPressed();
    }

    @Override
    public void onSuccessMascotas(List<MascotaDTO> listaMascotasDTO) {}

    @Override
    public void onSuccessMascota(MascotaDTO mascotaDTO) {
        //Si ha devuelto una mascota exitosamente, cargo su información
        dni.setText(mascotaDTO.getDNI());
        nombre.setText(mascotaDTO.getNombre());
        edad.setText(Integer.toString(mascotaDTO.getEdad()));
        especie.setText(mascotaDTO.getEspecie());
        raza.setText(mascotaDTO.getRaza());
        dni_especialista.setText(mascotaDTO.getDNI_especialista());

        //Realizo la petición para obtener los clientes de la mascota
        TenerController.getTenerPorDNI_Mascota(mascotaDTO, this, this);

    }

    @Override
    public void onSuccessModMascota(String mensaje) {}

    @Override
    public void onSuccess(List<TenerDTO> listaTener) {
        List<Cliente> listaClientes = new ArrayList<>();

        for(TenerDTO tener: listaTener) {
            listaClientes.add(tener.getCliente());
        }

        if(listView_clientes.getAdapter() == null) {
            adaptador = new AdaptadorClientes(this, listaClientes);
            listView_clientes.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }

    }

    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void ruedaAjustes(View view) {
        Intent ajustes = new Intent(this, Ajustes.class);
        startActivity(ajustes);
    }
}