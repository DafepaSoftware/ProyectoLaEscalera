package com.software.dafepa.proyectolaescalera;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by pablolira on 9/2/18.
 */

public class HalpFuncs {
    private HalpFuncs(){}



    //Fuente del titulo
    private static Typeface fontHalpMe = null;

    //Use
    public static void init(Activity a){
        fontHalpMe = Typeface.createFromAsset(a.getAssets(),"fonts/Pacifico-Regular.ttf");
    }

    //Deja la status bar translucida
    public static void translucentStatusBar(Activity a){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = a.getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public static Typeface getFontHalpMe(){
        return fontHalpMe;
    }

}
