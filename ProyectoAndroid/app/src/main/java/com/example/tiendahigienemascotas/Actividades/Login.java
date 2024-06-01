package com.example.tiendahigienemascotas.Actividades;

import static com.example.tiendahigienemascotas.BBDD.ImagenesContract.SQL_CREATE_TABLE;
import static com.example.tiendahigienemascotas.BBDD.ImagenesContract.SQL_DELETE_TABLE;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendahigienemascotas.BBDD.ImagenDAO;
import com.example.tiendahigienemascotas.BBDD.ImagenesDBHelper;
import com.example.tiendahigienemascotas.BBDD.entidades.Imagen;
import com.example.tiendahigienemascotas.CallBacks.LoginCallBack;
import com.example.tiendahigienemascotas.Controladores.CuentaController;
import com.example.tiendahigienemascotas.Modelos.CuentaDTO;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.example.tiendahigienemascotas.R;
import com.example.tiendahigienemascotas.Regex;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity implements LoginCallBack {
    EditText ETcorreo;
    EditText ETContraseña;
    ImagenesDBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Si no hay ningún idioma seleccionado, establezco el castellano por defecto y recargo para setear el idioma
        if(PreferenciasCompartidas.obtenerCodigoIdioma(this) == null) {
            PreferenciasCompartidas.guardarCodigoIdioma(this, "es");
            Ajustes.establecerIdiomaActividad(Login.this, PreferenciasCompartidas.obtenerCodigoIdioma(Login.this));
            recreate();
            return;
        }

        //Si el idioma seleccionado no coincide con el Locale que tiene esta actividad, recargo para setear el idioma bien
        if(!PreferenciasCompartidas.obtenerCodigoIdioma(Login.this).equals(Ajustes.obtenerIdiomaActividad(Login.this))) {
            Ajustes.establecerIdiomaActividad(Login.this, PreferenciasCompartidas.obtenerCodigoIdioma(Login.this));
            recreate();
            return;
        }

        //Instancio el dbHelper para utilizar la BD
        dbHelper = new ImagenesDBHelper(getBaseContext());

        //Borro la tabla de imágenes y la vuelvo a crear
        //Para poder insertar las imágenes de nuevo en caso de abrir la app y evitar así duplicados
        db = dbHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_TABLE);
        db.execSQL(SQL_CREATE_TABLE);
        db.close();

        //Creo un array con los bitmaps con las imágenes que inserto en SQLite
        List<Bitmap> listaBitmaps = new ArrayList<>();
        listaBitmaps.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.hombre));
        listaBitmaps.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.mujer));

        //Cada bitmap lo convierto a array de bytes e inserto cada imagen en la BD
        for(Bitmap bitmapImagen: listaBitmaps) {
            insertarImagenesSQLite(bitmapABitArray(bitmapImagen));
        }

        //Establezco la IP por defecto
        PreferenciasCompartidas.guardarIP(this, "192.168.68.101");

        //Compruebo si hay una cuenta loggeada y si existe. Si se cumplen ambas, loggeo al usuario
        CuentaController.existeCuentaLoggeada(this, this);

        ETcorreo = findViewById(R.id.ETLoginCorreo);
        ETContraseña = findViewById(R.id.ETLoginContraseña);

        //Limpio los 2 campos
        ETcorreo.setText("");
        ETContraseña.setText("");

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
                public void onSuccess(CuentaDTO cuentaDTO) {
                    //Si entra al onSuccess es porque la cuenta ya existe en la BD y aviso al usuario
                    Toast.makeText(Login.this, "Ya existe una cuenta con el correo proporcionado", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(String mensaje) {
                    //Si entra al onError es porque la cuenta aún no existe en la BD y la registro
                    CuentaController.registrar(ETcorreo.getText().toString(), ETContraseña.getText().toString(),
                            Login.this, new LoginCallBack() {
                                @Override
                                public void onSuccess(CuentaDTO cuentaDTO) {}

                                @Override
                                public void onSuccessRegistro(String mensaje) {
                                    //Muestro un mensaje de cuenta registrada
                                    Toast.makeText(Login.this, mensaje, Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void existeCuentaLoggeada(boolean existe) {}

                                @Override
                                public void onError(String mensaje) {
                                    //Muestro un mensaje de que no se ha podido registrar la cuenta
                                    Toast.makeText(Login.this, mensaje, Toast.LENGTH_LONG).show();
                                }
                            });
                }

                @Override
                public void onSuccessRegistro(String mensaje) {}

                @Override
                public void existeCuentaLoggeada(boolean existe) {}

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
    public void onSuccess(CuentaDTO cuentaDTO) {
        if (cuentaDTO.getContrasenha().equals(ETContraseña.getText().toString())) {
            PreferenciasCompartidas.limpiarPreferenciasCompartidasLogin(this);
            PreferenciasCompartidas.guardarCorreoEncriptado(this, cuentaDTO.getCorreo());
            Intent inicio = new Intent(this, Inicio.class);
            startActivity(inicio);
        } else {
            Toast.makeText(this, "La contraseña introducida no es correcta", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccessRegistro(String mensaje) {}

    @Override
    public void existeCuentaLoggeada(boolean existe) {
        if(existe) {
            Intent inicio = new Intent(this, Inicio.class);
            startActivity(inicio);
        }
    }

    @Override
    public void onError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void comprobarCuentaLoggeada(Context contexto) {
        CuentaController.existeCuentaLoggeada(contexto, new LoginCallBack() {
            @Override
            public void existeCuentaLoggeada(boolean existe) {
                if(!existe) {
                    PreferenciasCompartidas.limpiarPreferenciasCompartidasLogin(contexto);
                    Intent login = new Intent(contexto, Login.class);
                    startActivity(login);
                }
            }
            @Override
            public void onSuccess(CuentaDTO cuentaDTO) {}

            @Override
            public void onSuccessRegistro(String mensaje) {}

            @Override
            public void onError(String mensaje) {}
        });
    }

    private byte[] bitmapABitArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void insertarImagenesSQLite(byte[] bytesImagen){
        //Obtengo la BD en su "forma editable"
        db = dbHelper.getWritableDatabase();

        //Instancio la imagen que insertaré en la BD
        Imagen imagen = new Imagen(bytesImagen);

        ImagenDAO.InsertarImagen(db, imagen);

        db.close();
    }



}
