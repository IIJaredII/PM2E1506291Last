    package com.example.pm2e1506291.configuracion;

    public class PaisesContract {
        public static String TABLE_NAME ="paises";
        public static String COLUMN_ID="id";
        public static String COLUMN_NOMBRE="nombre";
        public static String COLUMN_CODIGO="codigo";
        public static String COLUMN_LOGITUD="longitud";

        public static final String CREATE_TABLE_PAISES="CREATE TABLE "+
                TABLE_NAME+" ("+
                COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_NOMBRE+" TEXT,"+
                COLUMN_CODIGO+" TEXT,"+
                COLUMN_LOGITUD+" INTEGER)";

        public static final String DROP_TABLE_PAISES ="DROP TABLE IF EXISTS "+TABLE_NAME;

        public static final String INSERT_PAISES = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_NOMBRE + ", " +
                COLUMN_CODIGO + ", " +
                COLUMN_LOGITUD + ") VALUES " +
                "('Honduras', '+504', 8), " +
                "('México', '+52', 10), " +
                "('Estados Unidos', '+1', 10), " +
                "('Guatemala', '+502', 8), " +
                "('El Salvador', '+503', 8), " +
                "('Costa Rica', '+506', 8), " +
                "('Nicaragua', '+505', 8), " +
                "('Panamá', '+507', 8), " +
                "('Colombia', '+57', 10), " +
                "('Argentina', '+54', 10);";
    }


