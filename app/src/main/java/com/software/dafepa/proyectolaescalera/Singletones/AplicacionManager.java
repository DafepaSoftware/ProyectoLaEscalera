package com.software.dafepa.proyectolaescalera.Singletones;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;

import com.software.dafepa.proyectolaescalera.Objects.Evento;


public class AplicacionManager {
    private static AplicacionManager instance = null;

    private int screen_width_ = 0;
    private int screen_height_ = 0;

    private Typeface main_font_= null;
    //private Typeface neuropool_ = null;

    private Activity a_init_screen_ = null;
    private Boolean show_dialog_ = true;

    private Evento evento;

    private AplicacionManager() {
        // Exists only to defeat instantiation.
    }
    public static AplicacionManager getInstance() {
        if(instance == null) {
            instance = new AplicacionManager();
        }
        return instance;
    }


    //Funcion para inicializar el singleton
    public void init(Activity a){
        a_init_screen_ = a;
        //Reogida de tamano de pantalla
        Display display = a.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screen_width_ = size.x;
        screen_height_ = size.y;

    }



    public Activity getInitScreen(){
        return a_init_screen_;
    }

    public int getScreen_width() {
        return screen_width_;
    }

    public int getScreen_height() {
        return screen_height_;
    }

    public Typeface getMainFont() {
        return main_font_;
    }

    public void dialogShowed(){
        show_dialog_ = false;
    }

    public boolean needToShow(){
        return  show_dialog_;
    }

    /*public Typeface getNeuropool() {
        return neuropool_;
    }*/

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
