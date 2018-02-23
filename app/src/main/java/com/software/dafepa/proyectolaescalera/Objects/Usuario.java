package com.software.dafepa.proyectolaescalera.Objects;

/**
 * Created by Fer on 23/2/18.
 */

public class Usuario {
    String mail;
    String nombre;
    String apellido;
    String contrasena;

    public Usuario(String mail, String nombre, String apellido, String contrasena) {
        this.mail = mail;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
