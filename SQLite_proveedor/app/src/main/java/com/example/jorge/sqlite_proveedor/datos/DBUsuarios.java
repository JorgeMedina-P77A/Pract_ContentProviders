package com.example.jorge.sqlite_proveedor.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jorge on 14/02/2018.
 */

public class DBUsuarios extends SQLiteOpenHelper{

    public static final String DB_NAME = "midbusuarios";
    private final String SCRIPT_TABLE_USUARIOS= "create table usuarios (" +
            "_id integer primary key autoincrement," +
            "nombre text not null," +
            "email text not null," +
            "contrasenia text not null" +
            ")";

    public static final String[] COLUMNAS_TABLE_USUARIOS= new String[]{"_id", "nombre", "email", "contrasenia"};
    public static final String TABLE_NAME_USUARIOS = "usuarios";

    public DBUsuarios(Context ctx) {
        super(ctx, DB_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCRIPT_TABLE_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




}
