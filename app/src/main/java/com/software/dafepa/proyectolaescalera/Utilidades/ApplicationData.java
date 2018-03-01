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

    private Usuario user;
    private boolean rememberme;

    public ApplicationData(){
        user = new Usuario();
        rememberme = false;
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
        ApplicationData aplicacion = null;

        Gson gson = new Gson();
        Type type = new TypeToken<ApplicationData>() {}.getType();
        ApplicationData obj = gson.fromJson(carga, type);
        if(obj!=null) {
            aplicacion=obj;

            /*this.usuario=obj.getUsuario();
            this.peticiones=obj.getPeticiones();
            this.contactosSoporte=obj.getContactosSoporte();
            this.primeraVez=obj.isPrimeraVez();
            this.numeroEjecutadaAPP=obj.getNumeroEjecutadaAPP();
            this.numeroVecesMostrarValoracionApp=obj.getNumeroVecesMostrarValoracionApp();
            this.mostrarAvisoValoracionAPP=obj.isMostrarAvisoValoracionAPP();*/




        }


    }
}
