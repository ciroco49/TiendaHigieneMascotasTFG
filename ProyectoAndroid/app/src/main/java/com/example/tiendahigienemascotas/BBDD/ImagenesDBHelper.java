package com.example.tiendahigienemascotas.BBDD;

import static com.example.tiendahigienemascotas.BBDD.ImagenesContract.SQL_CREATE_TABLE;
import static com.example.tiendahigienemascotas.BBDD.ImagenesContract.SQL_DELETE_TABLE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ImagenesDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "imagen.db";
    public static Context context;

    public ImagenesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
