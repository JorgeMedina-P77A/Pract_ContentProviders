package com.example.jorge.sqlite_proveedor.datos;

import com.example.jorge.sqlite_proveedor.Modelo.Usuario;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Created by Jorge on 14/02/2018.
 */

public class UsuariosDAO {

    public DBUsuarios _db = null;
    private SQLiteDatabase _ad = null;

    public UsuariosDAO(Context ctx) {
        _db = new DBUsuarios(ctx);
        _ad = _db.getWritableDatabase();
    }

    public boolean insert(Usuario usuario) {

        ContentValues cv = new ContentValues();
        cv.put(DBUsuarios.COLUMNAS_TABLE_USUARIOS[1], usuario.getNombre());
        cv.put(DBUsuarios.COLUMNAS_TABLE_USUARIOS[2], usuario.getEmail());
        cv.put(DBUsuarios.COLUMNAS_TABLE_USUARIOS[3], usuario.getContrasenia());

        try {
            return _ad.insert(DBUsuarios.TABLE_NAME_USUARIOS, null, cv) > 0;
        } catch (SQLiteException e) {
            throw e;
        }

    }

    public boolean update(Usuario usuario) {

        ContentValues cv = new ContentValues();
        cv.put(DBUsuarios.COLUMNAS_TABLE_USUARIOS[1], usuario.getNombre());
        cv.put(DBUsuarios.COLUMNAS_TABLE_USUARIOS[2], usuario.getEmail());
        cv.put(DBUsuarios.COLUMNAS_TABLE_USUARIOS[3], usuario.getContrasenia());

        try {

            return _ad.update(DBUsuarios.TABLE_NAME_USUARIOS ,cv, "_id=" + usuario.getId(), null) > 0;

        } catch (SQLiteException e) {
            throw e;

        }

    }

    public boolean delete(int id) {

        try {
            return _ad.delete(DBUsuarios.TABLE_NAME_USUARIOS, "_id=" + id, null)>0;
        } catch (SQLiteException e) {
            throw e;
        }

    }

    public Usuario getOneByID(int id) {

        Usuario result = null;
        Cursor c=null;

        try {

            c = _ad.query(DBUsuarios.TABLE_NAME_USUARIOS, DBUsuarios.COLUMNAS_TABLE_USUARIOS, "_id=" + id,
                            null, null, null, null);

            if(c.moveToFirst()){
                result = new Usuario(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
            }

        } catch (SQLiteException e) {
            throw e;
        }

        return result;

    }

    public Cursor getOneCursorByID(int id) {

        Usuario result = null;
        Cursor c=null;

        try {

            c = _ad.query(DBUsuarios.TABLE_NAME_USUARIOS, DBUsuarios.COLUMNAS_TABLE_USUARIOS, "_id=" + id,
                            null, null, null, null);

        } catch (SQLiteException e) {
            throw e;
        }

        return c;
    }

    public Cursor getAll() {

        Cursor c=null;

        try {

            c = _ad.rawQuery("select _id,nombre,email,contrasenia from usuarios", null);

        } catch (SQLiteException e) {
            throw e;
        }

        return c;

    }

    public Cursor getUserByCriterio(String cruterio){

        Cursor c =null;

        _ad .query(DBUsuarios.TABLE_NAME_USUARIOS, DBUsuarios.COLUMNAS_TABLE_USUARIOS, "nombre like %?% " ,
                new String[]{cruterio},null,null,null);

        return c;

    }

}
