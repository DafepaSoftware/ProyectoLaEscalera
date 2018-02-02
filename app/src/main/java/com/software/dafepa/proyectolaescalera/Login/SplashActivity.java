package com.software.dafepa.proyectolaescalera.Login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.software.dafepa.proyectolaescalera.PantallaPrincipal;
import com.software.dafepa.proyectolaescalera.R;
import com.software.dafepa.proyectolaescalera.Singletones.AplicacionManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout ly_logo_contenedor;
    private int desplazamiento_y;
    private boolean show_menu = true;
    private int a = 0;
    private LinearLayout ly_menu;
    private TextView img_titulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ly_logo_contenedor = (LinearLayout) findViewById(R.id.ly_logo_contenedor);
        ly_menu = (LinearLayout) findViewById(R.id.ly_menu);
        ly_menu.setVisibility(View.GONE);
        img_titulo = (TextView) findViewById(R.id.img_titulo);
        Typeface font = Typeface.createFromAsset(this.getAssets(),"fonts/Pacifico-Regular.ttf");
        img_titulo.setTypeface(font);



        animacion();

       /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, 4000);*/



    }


    private void animacion(){
        final long duracion_fadein = 800;
        final int duracion_desplazamiento = 500;

        desplazamiento_y =  -(AplicacionManager.getInstance().getScreen_height()*570)/2960;

        YoYo.with(Techniques.ZoomInDown).duration(duracion_fadein).repeat(0).playOn(ly_logo_contenedor);


        new CountDownTimer(duracion_fadein+100, duracion_fadein) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                YoYo.with(Techniques.Pulse).duration(duracion_fadein).repeat(0).playOn(ly_logo_contenedor);
                new CountDownTimer(duracion_fadein, duracion_fadein) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        if (show_menu) {
                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(
                                    Glider.glide(Skill.CubicEaseOut, 2000,
                                            ObjectAnimator.ofFloat(ly_logo_contenedor, "translationY", 0, desplazamiento_y))

                            );

                            set.setDuration(duracion_desplazamiento);
                            set.start();




                            new CountDownTimer(duracion_desplazamiento, 1 ){

                                public void onTick(long millisUntilFinished) {
                                    a+=5;
                                    if(a >255){
                                        a = 255;
                                    }
                                    //ly_logo_contenedor.setBackgroundColor(Color.argb(a, 145, 132, 214));
                                }

                                @Override
                                public void onFinish() {

                                }
                            }.start();
                            showMenu();
                        }else{





                        }
                    }
                }.start();

            }
        }.start();
    }

    public void showMenu(){

        ly_menu.setVisibility(View.VISIBLE);
        ly_menu.setAlpha(0);
        final int duracion_fade = 500;
        YoYo.with(Techniques.FadeIn).duration(duracion_fade).repeat(0).playOn(ly_menu);
        new CountDownTimer(duracion_fade+100, duracion_fade + 100) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                /*btn_registro.setEnabled(true);
                btn_iniciosesion.setEnabled(true);
                btn_ayuda.setEnabled(true);*/
            }
        }.start();
    }
}
