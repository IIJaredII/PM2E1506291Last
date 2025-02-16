package com.example.pm2e1506291.DAO;

import com.example.pm2e1506291.configuracion.PaisesContract;

public class PaisesDao {
    public static String ObtenerCodigoPorId = "SELECT codigo FROM "+PaisesContract.TABLE_NAME+" WHERE id = ?";
    public static String ObtenerPaises = "SELECT * FROM "+ PaisesContract.TABLE_NAME;
}
