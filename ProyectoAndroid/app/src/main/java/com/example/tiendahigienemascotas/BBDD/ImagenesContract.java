package com.example.tiendahigienemascotas.BBDD;

import android.provider.BaseColumns;

public class ImagenesContract {

    private ImagenesContract() {}

    public abstract static class ImagenesImagen implements BaseColumns {
        public static final String TABLE_NAME = "Imagen";
        public static final String COLUMN_NAME_BYTES = "BYTES";
    }

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + ImagenesImagen.TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ImagenesImagen.COLUMN_NAME_BYTES + " BLOB)";

    public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + ImagenesImagen.TABLE_NAME;

}
