package com.example.pm2e1506291.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.pm2e1506291.DAO.ContactosDao;
import com.example.pm2e1506291.Models.ContactosModel;
import com.example.pm2e1506291.configuracion.ContactosContract;
import com.example.pm2e1506291.configuracion.SQLiteConexion;

import java.util.ArrayList;

public class ContactosRepository {
    Context context;

    public ContactosRepository(Context context) {
        this.context = context;
    }

    public ArrayList<ContactosModel> mostrarContactsos(){
        SQLiteConexion conexion = new SQLiteConexion(context);

        SQLiteDatabase db = conexion.getWritableDatabase();
        ArrayList<ContactosModel> listaContactos = new ArrayList<>();
        ContactosModel contactos=null;
        Cursor cursor;

        cursor=db.rawQuery(ContactosDao.SELECT_ALL,null);

        if(cursor.moveToFirst()){
            do{
            contactos = new ContactosModel();
            contactos.setId(cursor.getInt(0));
            contactos.setNombre(cursor.getString(1));
            contactos.setIdpais(cursor.getInt(2));
            contactos.setNumero(cursor.getString(3));
            contactos.setNota(cursor.getString(5));
            contactos.setImagen(cursor.getString(5));
            contactos.setFechacreacion(cursor.getString(6));
            listaContactos.add(contactos);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaContactos;
    }

    public void AddContact(String nombre, String numero, String nota, int idpais, String imagenBit) {
        try {
            SQLiteConexion conexion = new SQLiteConexion(context);
            SQLiteDatabase db = conexion.getWritableDatabase();
            ContentValues valores = new ContentValues();

            valores.put(ContactosContract.COLUMN_NOMBRE, nombre);
            valores.put(ContactosContract.COLUMN_NUMERO, numero);
            valores.put(ContactosContract.COLUMN_NOTA, nota);
            valores.put(ContactosContract.COLUMN_ID_PAISES, idpais);
            valores.put(ContactosContract.COLUMN_IMAGEN, imagenBit);


            long Result = db.insert(ContactosContract.TABLE_NAME,null,valores);
            if(Result == -1){
                Toast.makeText(context,"Fallo",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"Exito",Toast.LENGTH_SHORT).show();
            }
            db.close();
            Toast.makeText(context,"Exitos #2",Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(context,ex.toString(),Toast.LENGTH_SHORT).show();
            Log.d("this", ex+"");
        }
    }

    public void UpdateContact(int id, String nombre, String numero, String nota, int idpais, String imagenBit) {
        SQLiteConexion conexion = new SQLiteConexion(context);
        SQLiteDatabase db = conexion.getWritableDatabase();
        try {
            ContentValues valores = new ContentValues();
            valores.put(ContactosContract.COLUMN_NOMBRE, nombre);
            valores.put(ContactosContract.COLUMN_NUMERO, numero);
            valores.put(ContactosContract.COLUMN_NOTA, nota);
            valores.put(ContactosContract.COLUMN_ID_PAISES, idpais);
            valores.put(ContactosContract.COLUMN_IMAGEN, imagenBit);

            String clausulaWhere = ContactosContract.COLUMN_ID + "=?";
            String[] argumento = {String.valueOf(id)};

            int filasActualizadas = db.update(ContactosContract.TABLE_NAME, valores, clausulaWhere, argumento);

            if (filasActualizadas > 0) {
                Toast.makeText(context, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "No actualizado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(context, "Error: " + ex.toString(), Toast.LENGTH_SHORT).show();
            Log.e("SQLiteError", ex.toString());
        } finally {
            db.close();
        }
    }

    public void deleteContact(int idContacto) {
        SQLiteConexion conexion = new SQLiteConexion(context);
        SQLiteDatabase db = conexion.getWritableDatabase();
        try {
            String clausulaWhere = ContactosContract.COLUMN_ID + "=?";
            String[] argumento = {String.valueOf(idContacto)};

            int filasEliminadas = db.delete(ContactosContract.TABLE_NAME, clausulaWhere, argumento);

            if (filasEliminadas > 0) {
                Toast.makeText(context, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "No se logro eliminar", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(context, "Error: " + ex.toString(), Toast.LENGTH_SHORT).show();
            Log.e("SQLiteError", ex.toString());
        } finally {
            db.close();
        }


    }
    public ContactosModel obtenerContactoPorId(int idContacto) {
        SQLiteConexion conexion = new SQLiteConexion(context);
        SQLiteDatabase db = conexion.getReadableDatabase();
        ContactosModel contacto = null;
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + ContactosContract.TABLE_NAME + " WHERE " + ContactosContract.COLUMN_ID + "=?";
            String[] argumentos = {String.valueOf(idContacto)};

            cursor = db.rawQuery(query, argumentos);

            if (cursor.moveToFirst()) {
                contacto = new ContactosModel();
                contacto.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ContactosContract.COLUMN_ID)));
                contacto.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(ContactosContract.COLUMN_NOMBRE)));
                contacto.setIdpais(cursor.getInt(cursor.getColumnIndexOrThrow(ContactosContract.COLUMN_ID_PAISES)));
                contacto.setNumero(cursor.getString(cursor.getColumnIndexOrThrow(ContactosContract.COLUMN_NUMERO)));
                contacto.setNota(cursor.getString(cursor.getColumnIndexOrThrow(ContactosContract.COLUMN_NOTA)));
                contacto.setImagen(cursor.getString(cursor.getColumnIndexOrThrow(ContactosContract.COLUMN_IMAGEN)));
                contacto.setFechacreacion(cursor.getString(cursor.getColumnIndexOrThrow(ContactosContract.COLUMN_FECHA_CREACION)));
            }
        } catch (Exception ex) {
            Log.e("SQLiteError", "Error al obtener contacto por ID: " + ex.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return contacto;
    }

}
