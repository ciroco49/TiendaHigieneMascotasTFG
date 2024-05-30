package com.example.tiendahigienemascotas.Actividades;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendahigienemascotas.CallBacks.LoginCallBack;
import com.example.tiendahigienemascotas.Controladores.CuentaController;
import com.example.tiendahigienemascotas.Modelos.Cuenta;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.example.tiendahigienemascotas.R;

public class Inicio extends AppCompatActivity {
    Dialog popup_Clientes, popup_Mascotas, popup_Especialistas;
    Button btnConsultarClientes, btnModificarClientes,
            btnConsultarMascotas, btnModificarMascotas,
            btnConsultarEspecialistas, btnModificarEspecialistas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Creo el popup de clientes
        popup_Clientes = new Dialog(Inicio.this);
        popup_Clientes.setContentView(R.layout.popup_clientes);
        popup_Clientes.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnConsultarClientes = popup_Clientes.findViewById(R.id.btnConsultarClientes);
        btnModificarClientes = popup_Clientes.findViewById(R.id.btnModificarClientes);
        //Creo el popup de mascotas
        popup_Mascotas = new Dialog(Inicio.this);
        popup_Mascotas.setContentView(R.layout.popup_mascotas);
        popup_Mascotas.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnConsultarMascotas = popup_Mascotas.findViewById(R.id.btnConsultarMascotas);
        btnModificarMascotas = popup_Mascotas.findViewById(R.id.btnModificarMascotas);
        //Creo el popup de especialistas
        popup_Especialistas = new Dialog(Inicio.this);
        popup_Especialistas.setContentView(R.layout.popup_especialistas);
        popup_Especialistas.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnConsultarEspecialistas = popup_Especialistas.findViewById(R.id.btnConsultarEspecialistas);
        btnModificarEspecialistas = popup_Especialistas.findViewById(R.id.btnModificarEspecialistas);


    }
        public void consultarClientes(View view) {
            Intent clientes = new Intent(this, ConsultarClientes.class);
            startActivity(clientes);
        }

        public void modificarClientes(View view) {
            Intent modificarClientes = new Intent(this, ModificarCliente.class);
            startActivity(modificarClientes);
        }

        public void popUpClientes(View view) {
            popup_Clientes.show();
        }

        public void consultarMascotas(View view) {
            //Intent para consultar
        }

        public void modificarMascotas(View view) {
            //Intent para modificar
        }

        public void popUpMascotas(View view) {
            popup_Mascotas.show();
        }

        public void consultarEspecialistas(View view) {
            //Intent para consultar
        }

        public void modificarEspecialistas(View view) {
            //Intent para modificar
        }

        public void popUpEspecialistas(View view) {
            popup_Especialistas.show();
        }

    public void ruedaAjustes(View view) {
        Intent ajustes = new Intent(this, Ajustes.class);
        startActivity(ajustes);
    }

    @Override
    public void onBackPressed() {
        //Le indico que no puede volver al Login sin cerrar sesión
        Toast.makeText(this, "Debe cerrar la sesión en ajustes para volver al login", Toast.LENGTH_SHORT).show();
        //Hago que solo pueda volver si se cumple una condición imposible
        if (false) {
            super.onBackPressed();
        }
    }


}
