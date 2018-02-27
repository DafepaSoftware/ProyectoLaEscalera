package com.software.dafepa.proyectolaescalera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pablolira on 9/2/18.
 */


//Funciones útiles
public class HalpFuncs {
    private HalpFuncs(){}

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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

    //Muestra el teclado de Android por pantalla
    public static void showKeyboard(Activity a){
        hideKeyboard(a);
        InputMethodManager imm = (InputMethodManager) a.getSystemService(a.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //Oculta el teclado de Android
    public static void hideKeyboard(Activity a){
        View view = a.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)a.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //comprueba que un string tiene un formato de correo electrónico válido
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
}
