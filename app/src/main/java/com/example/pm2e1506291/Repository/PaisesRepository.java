package com.example.pm2e1506291.Repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.ParcelUuid;
import android.util.Log;

import com.example.pm2e1506291.DAO.PaisesDao;
import com.example.pm2e1506291.Models.PaisesModel;
import com.example.pm2e1506291.configuracion.ContactosContract;
import com.example.pm2e1506291.configuracion.PaisesContract;
import com.example.pm2e1506291.configuracion.SQLiteConexion;

import java.util.ArrayList;

public class PaisesRepository {
    private static Context context;

    public PaisesRepository(Context context) {
        this.context = context;
    }

    public  String obtenerCodigoPorIdContacto(String idContacto) {
        SQLiteConexion conexion = new SQLiteConexion(context);
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cursor cursor=null;
        String codigoPais = null;

        String query="SELECT " + PaisesContract.COLUMN_CODIGO +
                " FROM " + PaisesContract.TABLE_NAME +
                " WHERE c." + PaisesContract.COLUMN_ID + " = ?";

        String[] whereArgs = {String.valueOf(idContacto)};

        cursor = db.rawQuery(query, whereArgs);

        if (cursor.moveToFirst()) {
            codigoPais = cursor.getString(0);
        }

        return codigoPais;
    }

    public static String obtenerCodigoPorId(int idContacto) {
        SQLiteConexion conexion = new SQLiteConexion(context);
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cursor cursor = null;
        String codigoPais = null;

        try {
            cursor = db.rawQuery(PaisesDao.ObtenerCodigoPorId, new String[]{String.valueOf(idContacto)});

            if (cursor != null && cursor.moveToFirst()) {
                codigoPais = cursor.getString(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return codigoPais;
    }

    public ArrayList<PaisesModel> ObtenerPaises() {
        SQLiteConexion conexion = new SQLiteConexion(context);
        SQLiteDatabase db = conexion.getReadableDatabase();
        ArrayList<PaisesModel> listaPaises = new ArrayList<>();

        Cursor cursor = db.rawQuery(PaisesDao.ObtenerPaises, null);

        if (cursor != null) {
            while (cursor.moveToNext()) { // Moverse por todos los registros
                PaisesModel pais = new PaisesModel();
                pais.setId(cursor.getInt(0)); // ID
                pais.setNombre(cursor.getString(1)); // Nombre
                pais.setCodigo(cursor.getString(2)); // Código
                pais.setLongitud(cursor.getInt(3)); // Longitud
                listaPaises.add(pais);
            }
            cursor.close();
        }

        db.close();
        return listaPaises;
    }
}
