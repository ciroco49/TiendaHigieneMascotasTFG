package com.example.tiendahigienemascotas.BBDD;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.tiendahigienemascotas.BBDD.entidades.Imagen;

import java.util.ArrayList;

public class ImagenDAO {

    //Recibo la imagen, creo el insert con los datos de la imagen que inserto y la inserto en la BD
    public static void InsertarImagen(SQLiteDatabase db, Imagen imagen) {
        ContentValues valores = new ContentValues();

        valores.put(ImagenesContract.ImagenesImagen.COLUMN_NAME_BYTES, imagen.getImagenBytes());

        long newFilaID = db.insert(ImagenesContract.ImagenesImagen.TABLE_NAME, null, valores);
    }

    public static ArrayList<Imagen> getImagenes(SQLiteDatabase db) {

        String[] select = {
                BaseColumns._ID,
                ImagenesContract.ImagenesImagen.COLUMN_NAME_BYTES
        };

        Cursor cursor = db.query(
                ImagenesContract.ImagenesImagen.TABLE_NAME,
                select,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Imagen> listaImagenes = new ArrayList();

        while(cursor.moveToNext()) {
            int imagenID = cursor.getInt(cursor.getColumnIndexOrThrow(ImagenesContract.ImagenesImagen._ID));
            byte[] bytesImagen = cursor.getBlob(cursor.getColumnIndexOrThrow(ImagenesContract.ImagenesImagen.COLUMN_NAME_BYTES));

            listaImagenes.add(new Imagen(imagenID, bytesImagen));
        }

        return listaImagenes;

    }

}
