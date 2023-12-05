package com.example.hadmade.modelo;


import java.io.Serializable;

public class RopaModel implements Serializable {
    String nombre , precio ,surl;



    RopaModel(){

    }

    public RopaModel(String nombre, String precio, String surl) {
        this.nombre = nombre;
        this.precio = precio;
        this.surl = surl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }
}
