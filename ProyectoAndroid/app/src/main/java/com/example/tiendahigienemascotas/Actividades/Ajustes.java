package com.example.tiendahigienemascotas.Actividades;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendahigienemascotas.BBDD.ImagenDAO;
import com.example.tiendahigienemascotas.BBDD.ImagenesDBHelper;
import com.example.tiendahigienemascotas.BBDD.entidades.Imagen;
import com.example.tiendahigienemascotas.PreferenciasCompartidas;
import com.example.tiendahigienemascotas.R;
import com.example.tiendahigienemascotas.Regex;

import java.util.ArrayList;
import java.util.Locale;

public class Ajustes extends AppCompatActivity {
EditText IP;
Spinner spinner_idioma;
Spinner spinner_imagenes;
private static final String[] idiomas = {"Español", "Gallego"};
private static final String[] imagenes = {"Hombre", "Mujer"};
ImageView imagenSpinner;
ImagenesDBHelper dbHelper;
SQLiteDatabase db;
ArrayList<Imagen> listaImagenes = new ArrayList<>();
String codigoIdiomaSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_ajustes);

        //Si el idioma seleccionado no coincide con el Locale que tiene esta actividad, recargo para setear el idioma bien
        if(!PreferenciasCompartidas.obtenerCodigoIdioma(Ajustes.this).equals(Ajustes.obtenerIdiomaActividad(Ajustes.this))) {
            Ajustes.establecerIdiomaActividad(Ajustes.this, PreferenciasCompartidas.obtenerCodigoIdioma(Ajustes.this));
            recreate();
            return;
        }

        //Instancio el dbHelper para utilizar la BD
        dbHelper = new ImagenesDBHelper(getBaseContext());

        //Abro la base de datos de forma legible para obtener las imágenes de la BD de SQLite
        db =dbHelper.getReadableDatabase();
        listaImagenes = ImagenDAO.getImagenes(db);

        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        //Inicializo mis elementos
        spinner_idioma = findViewById(R.id.spinnerIdiomas);
        spinner_imagenes = findViewById(R.id.spinnerImagenes);

        ArrayAdapter<String> adaptadorIdiomas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, idiomas);
        adaptadorIdiomas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_idioma.setAdapter(adaptadorIdiomas);
        spinner_idioma.setSelection(0);

        ArrayAdapter<String> adaptadorImagenes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, imagenes);
        adaptadorImagenes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_imagenes.setAdapter(adaptadorImagenes);
        spinner_imagenes.setSelection(0);

        IP = findViewById(R.id.IP_ajustes);
        imagenSpinner = findViewById(R.id.imagenSpinner);

        spinner_idioma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idioma_seleccionado = (String) parent.getItemAtPosition(position);

                if(idioma_seleccionado.equals("Español")) {
                    codigoIdiomaSeleccionado = "es";
                } else {
                    codigoIdiomaSeleccionado = "gl";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinner_imagenes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String imagen_seleccionada = (String) parent.getItemAtPosition(position);

                if(imagen_seleccionada.equals("Hombre")) {
                    Imagen imagen = listaImagenes.get(0);
                    Bitmap bitmap = byteArrayABitmap(imagen.getImagenBytes());
                    imagenSpinner.setImageBitmap(bitmap);
                } else {
                    Imagen imagen = listaImagenes.get(1);
                    Bitmap bitmap = byteArrayABitmap(imagen.getImagenBytes());
                    imagenSpinner.setImageBitmap(bitmap);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


    }

    public void btnCerrarSesion(View view) {
        PreferenciasCompartidas.limpiarPreferenciasCompartidasLogin(this);
        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }

    @Override
    public void onBackPressed() {
        //Compruebo si hay una cuenta loggeada y si existe. Si no se cumple alguna llevo al usuario al Login
        new Login().comprobarCuentaLoggeada(this);

        super.onBackPressed();
    }

    public void aplicarCambios(View view) {
        String ipv4 = IP.getText().toString();
        //Si hay alguna IP escrita que cumpla el patrón requerido la guardo
        if(!ipv4.isEmpty() && Regex.validarIP(ipv4)) {
            PreferenciasCompartidas.guardarIP(this, ipv4);
        }


        //Guardo el código del idioma escogido en las preferencias para usarlo en mis actividades
        PreferenciasCompartidas.guardarCodigoIdioma(Ajustes.this,codigoIdiomaSeleccionado);

        //Si el idioma seleccionado no coincide con el Locale que tiene esta actividad se lo setteo
        if(!PreferenciasCompartidas.obtenerCodigoIdioma(Ajustes.this).equals(Ajustes.obtenerIdiomaActividad(Ajustes.this))) {
            Ajustes.establecerIdiomaActividad(Ajustes.this, PreferenciasCompartidas.obtenerCodigoIdioma(Ajustes.this));
        }





        //Recargo la pantalla para aplicar los cambios de idioma y/o imagen
        recreate();
    }

    public static void establecerIdiomaActividad(Activity actividad, String codigoIdioma) {
        //Creo el Locale (idoma) con el código del idioma y se lo establezco a la configuración de la actividad
        Locale locale = new Locale(codigoIdioma);
        locale.setDefault(locale);
        Resources recursos = actividad.getResources();
        Configuration config = recursos.getConfiguration();
        config.setLocale(locale);
        config.getLocales();
        recursos.updateConfiguration(config, recursos.getDisplayMetrics());
    }

    public static String obtenerIdiomaActividad(Activity actividad) {
        //Obtengo el Locale (idioma) establecido en la configuración de la actividad
        Resources resources = actividad.getResources();
        Configuration config = resources.getConfiguration();
        return config.getLocales().get(0).toString();
    }

    private Bitmap byteArrayABitmap(byte[] arrayBytes) {
        return BitmapFactory.decodeByteArray(arrayBytes, 0, arrayBytes.length);
    }

}