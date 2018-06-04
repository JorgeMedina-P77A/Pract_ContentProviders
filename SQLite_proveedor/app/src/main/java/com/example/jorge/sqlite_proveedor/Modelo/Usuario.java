package com.example.jorge.sqlite_proveedor.Modelo;

/**
 * Created by Jorge on 14/02/2018.
 */

public class Usuario {

    int id;
    String  nombre, email, contrasenia;

    public Usuario(int id, String nombre, String email, String contrasenia) {

        super();
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}
