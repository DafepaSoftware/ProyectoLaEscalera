package com.software.dafepa.proyectolaescalera.Objects;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Fer on 23/2/18.
 */

@IgnoreExtraProperties
public class Usuario {


    private String mail;
    private String nombre;
    private String apellido;
    private String contrasena;
    private String nick;

    public Usuario(){}

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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}