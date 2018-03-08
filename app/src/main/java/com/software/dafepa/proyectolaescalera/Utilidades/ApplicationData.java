package com.software.dafepa.proyectolaescalera.Utilidades;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.software.dafepa.proyectolaescalera.Objects.Usuario;

import java.lang.reflect.Type;

/**
 * Created by pablolira on 1/3/18.
 */

public class ApplicationData {

    private boolean rememberme;
    private String usuario_nick;

    public ApplicationData(){
        rememberme = false;
        usuario_nick = "";
    }

    public void guardarEnPreferencias(Activity a)
    {
        SharedPreferences preferences;
        Gson gson = new Gson();
        String json = gson.toJson(this);

        preferences=  a.getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("aplicacion", json);
        editor.commit();

    }

    public void cargarAplicacionDePreferencias(Activity a)
    {
        SharedPreferences preferences;
        preferences=  a.getSharedPreferences("Preferencias",Context.MODE_PRIVATE);
        String carga=preferences.getString("aplicacion", null);

        Gson gson = new Gson();
        Type type = new TypeToken<ApplicationData>() {}.getType();
        ApplicationData obj = gson.fromJson(carga, type);
        if(obj!=null) {

            rememberme = obj.isRememberme();
            usuario_nick = obj.getUsuario_nick();

            /*this.usuario=obj.getUsuario();
            this.peticiones=obj.getPeticiones();
            this.contactosSoporte=obj.getContactosSoporte();
            this.primeraVez=obj.isPrimeraVez();
            this.numeroEjecutadaAPP=obj.getNumeroEjecutadaAPP();
            this.numeroVecesMostrarValoracionApp=obj.getNumeroVecesMostrarValoracionApp();
            this.mostrarAvisoValoracionAPP=obj.isMostrarAvisoValoracionAPP();*/

        }


    }


    public boolean isRememberme() {
        return rememberme;
    }

    public void setRememberme(boolean rememberme) {
        this.rememberme = rememberme;
    }

    public String getUsuario_nick() {
        return usuario_nick;
    }

    public void setUsuario_nick(String usuario_nick) {
        this.usuario_nick = usuario_nick;
    }
}
