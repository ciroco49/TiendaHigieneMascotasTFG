package com.example.tiendahigienemascotas;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendahigienemascotas.CallBacks.LoginCallBack;
import com.example.tiendahigienemascotas.Controladores.CuentaController;
import com.example.tiendahigienemascotas.Modelos.Cuenta;


public class Login extends AppCompatActivity implements LoginCallBack {
    EditText ETcorreo;
    EditText ETContraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ETcorreo = findViewById(R.id.ETLoginCorreo);
        ETContraseña = findViewById(R.id.ETLoginContraseña);

    }

    public void registrar(View view) {

    }

    public void logear(View view) {
        try {
            //Compruebo que me proporcionen todos los campos
            if(ETcorreo.getText().toString().isEmpty() || ETContraseña.getText().toString().isEmpty()) {
                Toast.makeText(this, "Es obligatorio proporcionar un correo y contraseña", Toast.LENGTH_LONG).show();
            } else {
                //Intento logear
                CuentaController.login(ETcorreo.getText().toString(), ETContraseña.getText().toString(), this, this);
            }
        } catch (Exception ex) {
            Log.d("Error main: ", ex.getMessage());
        }


    }

    @Override
    public void onSuccess(Cuenta cuenta) {
        if(cuenta.getContrasenha().equals(ETContraseña.getText().toString())) {
            Toast.makeText(this, "LOGEADO, UN SALUDO", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "PON LA CONTRASEÑA BIEN, PAYASO", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}