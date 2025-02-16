package com.example.pm2e1506291.configuracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class SQLiteConexion extends SQLiteOpenHelper {
    public static final String NameDB="Contactos";
    public static final int version=2;
    public static final SQLiteDatabase.CursorFactory factory = null;

    public SQLiteConexion(@Nullable Context context) {
        super(context, NameDB, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PaisesContract.CREATE_TABLE_PAISES);
        db.execSQL(PaisesContract.INSERT_PAISES);
        db.execSQL(ContactosContract.CREATE_TABLE_CONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PaisesContract.DROP_TABLE_PAISES);
        db.execSQL(ContactosContract.DROP_TABLE_CONTACTOS);
    }
}
