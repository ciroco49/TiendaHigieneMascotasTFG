package com.example.tiendahigienemascotas.Actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendahigienemascotas.CallBacks.LoginCallBack;
import com.example.tiendahigienemascotas.Controladores.CuentaController;
import com.example.tiendahigienemascotas.Modelos.Cuenta;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.example.tiendahigienemascotas.R;
import com.example.tiendahigienemascotas.Regex;

public class Login extends AppCompatActivity implements LoginCallBack {
    EditText ETcorreo;
    EditText ETContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ETcorreo = findViewById(R.id.ETLoginCorreo);
        ETContraseña = findViewById(R.id.ETLoginContraseña);

        //Si ya hay un logeo activo en la cuenta
        if(PreferenciasCompartidas.obtenerCorreoDesencriptado(this) != null) {
            Intent inicio = new Intent(this, Inicio.class);
            startActivity(inicio);
        }

    }

    public void registrar(View view) {
        // Compruebo que me proporcionen todos los campos y que el correo cumpla el formato regex
        if (ETcorreo.getText().toString().isEmpty() || ETContraseña.getText().toString().isEmpty()) {
            Toast.makeText(this, "Es obligatorio proporcionar un correo y contraseña", Toast.LENGTH_LONG).show();
        } else if (!Regex.validarCorreo(ETcorreo.getText().toString())) {
            Toast.makeText(this, "El correo proporcionado no cumple el formato requerido", Toast.LENGTH_LONG).show();
        } else {
            //Compruebo que el correo no exista ya en la BD porque los correos son únicos para cada cuenta
            CuentaController.login(ETcorreo.getText().toString(), this, new LoginCallBack() {
                @Override
                public void onSuccess(Cuenta cuenta) {
                    //Si entra al onSuccess es porque la cuenta ya existe en la BD y aviso al usuario
                    Toast.makeText(Login.this, "Ya existe una cuenta con el correo proporcionado", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(String mensaje) {
                    //Si entra al onError es porque la cuenta aún no existe en la BD y la registro
                    CuentaController.registrar(ETcorreo.getText().toString(), ETContraseña.getText().toString(),
                            Login.this, new LoginCallBack() {
                                @Override
                                public void onSuccess(Cuenta cuenta) {}

                                @Override
                                public void onSuccessRegistro(String mensaje) {
                                    //Muestro un mensaje de cuenta registrada
                                    Toast.makeText(Login.this, mensaje, Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onError(String mensaje) {
                                    //Muestro un mensaje de que no se ha podido registrar la cuenta
                                    Toast.makeText(Login.this, mensaje, Toast.LENGTH_LONG).show();
                                }
                            });
                }

                @Override
                public void onSuccessRegistro(String mensaje) {}

            });
            CuentaController.login(ETcorreo.getText().toString(), this, this);
        }
    }

    public void logear(View view) {
        try {
            // Compruebo que me proporcionen todos los campos y que el correo cumpla el formato regex
            if (ETcorreo.getText().toString().isEmpty() || ETContraseña.getText().toString().isEmpty()) {
                Toast.makeText(this, "Es obligatorio proporcionar un correo y contraseña", Toast.LENGTH_LONG).show();
            } else if (!Regex.validarCorreo(ETcorreo.getText().toString())) {
                Toast.makeText(this, "El correo proporcionado no cumple el formato requerido", Toast.LENGTH_LONG).show();
            } else {
                // Intento logear
                CuentaController.login(ETcorreo.getText().toString(), this, this);
            }
        } catch (Exception ex) {
            Log.d("Error main: ", ex.getMessage());
        }
    }

    @Override
    public void onSuccess(Cuenta cuenta) {
        if (cuenta.getContrasenha().equals(ETContraseña.getText().toString())) {
            PreferenciasCompartidas.limpiarPreferenciasCompartidas(this);
            PreferenciasCompartidas.guardarCorreoEncriptado(this, cuenta.getCorreo());
            Intent inicio = new Intent(this, Inicio.class);
            startActivity(inicio);
        } else {
            Toast.makeText(this, "La contraseña introducida no es correcta", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccessRegistro(String mensaje) {}

    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

}
