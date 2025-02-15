package com.example.pm2e1506291.configuracion;

public class ContactosContract {
    public static String TABLE_NAME="contactos";
    public static String COLUMN_ID="id";
    public static String COLUMN_NOMBRE="nombre";
    public static String COLUMN_ID_PAISES="idpais";
    public static String COLUMN_NUMERO="numero";
    public static String COLUMN_IMAGEN="imagen";
    public static String COLUMN_FECHA_CREACION="fechacreacion";


    public static final String CREATE_TABLE_CONTACTOS="CREATE TABLE "+
            TABLE_NAME+" ("+
            COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_NOMBRE+" TEXT NOT NULL,"+
            COLUMN_ID_PAISES+" INTEGER NOT NULL,"+
            COLUMN_NUMERO + " INTEGER NOT NULL"+
            COLUMN_IMAGEN + " TEXT NOT NULL"+
            COLUMN_FECHA_CREACION+" TEXT NOT NULL)"+
            "FOREIGN KEY (" + COLUMN_ID_PAISES + ") REFERENCES "+PaisesContract.TABLE_NAME+"("+PaisesContract.COLUMN_ID+") ON DELETE CASCADE ON UPDATE CASCADE)";

    public static final String DROP_TABLE_CONTACTOS ="DROP TABLE IF EXISTS "+TABLE_NAME;


}
