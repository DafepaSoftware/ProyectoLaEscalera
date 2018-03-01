package com.software.dafepa.proyectolaescalera.Objects;

import android.graphics.drawable.Drawable;
import android.media.Image;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Fer on 23/2/18.
 */
@IgnoreExtraProperties
public class Evento {

    private String titulo;
    private String descripcion;
    private Boolean busco;
    private int tfno;
    private String nick_usuario;
    private Drawable img;
    private int ID;


    public Evento(){}

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

    public Boolean getBusco() {
        return busco;
    }

    public void setBusco(Boolean busco) {
        this.busco = busco;
    }

    public int getTfno() {
        return tfno;
    }

    public void setTfno(int tfno) {
        this.tfno = tfno;
    }

    public String getNick_usuario() {
        return nick_usuario;
    }

    public void setNick_usuario(String nick_usuario) {
        this.nick_usuario = nick_usuario;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}


