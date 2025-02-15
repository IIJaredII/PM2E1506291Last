package com.example.pm2e1506291.Models;

public class ContactosModel {

    private int id;
    private String nombre;
    private int idpais;
    private String numero;
    private String imagen;
    private String fechacreacion;

    public ContactosModel() {
    }

    public ContactosModel(int id, String nombre, int idpais, String numero, String imagen, String fechacreacion) {
        this.id = id;
        this.nombre = nombre;
        this.idpais = idpais;
        this.numero = numero;
        this.imagen = imagen;
        this.fechacreacion = fechacreacion;
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

    public int getIdpais() {
        return idpais;
    }

    public void setIdpais(int idpais) {
        this.idpais = idpais;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }
}
