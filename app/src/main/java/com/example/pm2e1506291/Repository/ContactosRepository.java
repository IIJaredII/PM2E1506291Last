package com.example.pm2e1506291.Repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pm2e1506291.Models.ContactosModel;
import com.example.pm2e1506291.configuracion.SQLiteConexion;

import java.util.ArrayList;

public class ContactosRepository {
    Context context;

    public ContactosRepository(Context context) {
        this.context = context;
    }
    SQLiteConexion conexion = new SQLiteConexion(context);

    SQLiteDatabase bd = conexion.getWritableDatabase();
    ArrayList<ContactosModel> listaContactos = new ArrayList<>();
    ContactosModel contactos=null;
    Cursor cursor;

}
