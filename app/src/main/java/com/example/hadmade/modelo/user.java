package com.example.hadmade.modelo;

public class user {

    private String nombre, ciudad,localidad,direccion,numero,postal,referencias ;
   private  String uid;
    public user(){

    }
    public user(String uid, String nombre, String ciudad, String localidad, String direccion, String numero, String postal, String referencias) {
       this.uid = uid;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.localidad = localidad;
        this.direccion = direccion;
        this.numero = numero;
        this.postal = postal;
        this.referencias = referencias;

    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    @Override
    public String toString(){
        return nombre;
    }
}
