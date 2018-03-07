package com.software.dafepa.proyectolaescalera.Objects;

import android.graphics.Bitmap;
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
    private Bitmap img;
    private int ID;
    private int codigo_postal;

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

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(int codigo_postal) {
        this.codigo_postal = codigo_postal;
    }
}


