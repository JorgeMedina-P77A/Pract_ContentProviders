package com.example.jorge.sqlite_proveedor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jorge.sqlite_proveedor.datos.UsuariosDAO;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_INSERT = 0;
    private static final int REQUEST_CODE_EDIT = 1;
    ListView lst;
    UsuariosDAO dao;
    private int _id;
    private int _idList;


    public void btnCPD_click(View v){

        ContentValues cv = new ContentValues();

        Uri uri = getContentResolver().insert(UserDictionary.Words.CONTENT_URI, cv);

        if (uri != null ){
            Log.d("CP", uri.toString());
        }else{
            Log.d("CP", "No inserto");
        }


        Cursor cd = getContentResolver().query(

                UserDictionary.Words.CONTENT_URI,
                new String[]{
                        UserDictionary.Words._ID,
                        UserDictionary.Words.WORD,
                        UserDictionary.Words.LOCALE
                },null,null,null

        );


        SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                        android.R.layout.simple_list_item_2,
                        cd,
                        new String[]{
                                UserDictionary.Words._ID,
                                UserDictionary.Words.WORD

                        },
                        new int[]{android.R.id.text1,android.R.id.text2},
                        android.widget.SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );




        Cursor cc = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,

                },
                null, null,null
        );

        SimpleCursorAdapter scac = new SimpleCursorAdapter(this,
                        android.R.layout.simple_list_item_2,
                        cc,
                        new String[]{
                                ContactsContract.Contacts._ID,
                                ContactsContract.Contacts.DISPLAY_NAME,
                        },
                        new int[]{android.R.id.text1,android.R.id.text2},
                        SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
                );

        lst.setAdapter(sca);

        cargarLista();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst= (ListView)findViewById(R.id.lst);
        lst.setOnCreateContextMenuListener(this);

        dao = new UsuariosDAO(getApplicationContext());

        cargarLista();

    }

    private void cargarLista () {

        Cursor c;

        try {

            c = dao.getAll();

            SimpleCursorAdapter adp = new SimpleCursorAdapter(getApplicationContext(),
                            R.layout.layout_item_usuario,
                            c, new String[]{"nombre","email"}
                            , new int[]{R.id.lblItemNombre, R.id.lblItemEmail},
                            SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

            lst.setAdapter(adp);

        } catch (Exception e) {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 0, 0, "Agregar").setIcon(R.mipmap.ic_launcher_round);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==0){
            Intent i = new Intent(getApplicationContext(), Registro.class);
            i.putExtra("accion", "insert");
            startActivityForResult(i, REQUEST_CODE_INSERT);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

        menu.add(0, 0, 0, "editar").setIcon(R.mipmap.ic_launcher_round);
        menu.add(0,1,1,"eliminar"); menu.getItem(1).setIcon(R.mipmap.ic_launcher_round);

        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo acmi =  (AdapterContextMenuInfo)item.getMenuInfo();
        Log.d("ACMI ID", "" + acmi.id) ;
        Log.d("ACMI POSITION", "" + acmi.position) ;

        SQLiteCursor  sqliteCursor  = (SQLiteCursor)lst.getItemAtPosition(acmi.position);

        Log.d("COLUMNA ID", "" + sqliteCursor.getInt(0) ) ;
        Log.d("COLUMNA NOMBRE", "" + sqliteCursor.getString(1) ) ;
        Log.d("COLUMNA EMAIL", "" + sqliteCursor.getString(2) ) ;

        //EDIT
        if (item.getItemId()==0){
            Intent i = new Intent(this, Registro.class);
            i.putExtra("accion", "edit");
            i.putExtra("id", sqliteCursor.getInt(0));
            startActivityForResult(i, REQUEST_CODE_EDIT);

        }

        //DEL
        if (item.getItemId()==1){
            this.eliminar(sqliteCursor.getInt(0));

        }

        return super.onContextItemSelected(item);

    }

    private void eliminar(int idUsuario) {

        _id = idUsuario;
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Elimniar usuario");
        adb.setMessage("Deseas eliminar el usuario?");
        adb.setIcon(R.mipmap.ic_launcher_round);
        adb.setPositiveButton("Si", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if( dao.delete(_id)){
                    cargarLista();
                    Toast.makeText(getBaseContext(), "EXITO", Toast.LENGTH_SHORT).show();
                }
            }

        });

        adb.setNegativeButton("No", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        adb.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode== REQUEST_CODE_INSERT){
            if (resultCode==RESULT_OK){
                cargarLista();
            }
        }

        if(requestCode==REQUEST_CODE_EDIT){
            if(resultCode==RESULT_OK){
                cargarLista();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }














}
