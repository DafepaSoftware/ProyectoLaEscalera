package com.software.dafepa.proyectolaescalera.Objects;

/**
 * Created by Fer on 23/2/18.
 */

public class Evento {
    String titulo;
    String descripcion;
    String mail;
    Boolean busco;

    public Evento(String titulo, String descripcion, String mail, Boolean busco) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.mail = mail;
        this.busco = busco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean getBusco() {
        return busco;
    }

    public void setBusco(Boolean busco) {
        this.busco = busco;
    }
}


