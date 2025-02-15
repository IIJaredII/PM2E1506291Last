package com.example.pm2e1506291.Models;

public class PaisesModel {

    private int id;
    private String nombre;
    private String codigo;
    private int longitud;

    public PaisesModel() {
    }

    public PaisesModel(int id, String nombre, String codigo, int longitud) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }
}
