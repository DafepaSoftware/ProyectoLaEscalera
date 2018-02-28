package com.software.dafepa.proyectolaescalera.Login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.software.dafepa.proyectolaescalera.HalpFuncs;
import com.software.dafepa.proyectolaescalera.PantallaPrincipal;
import com.software.dafepa.proyectolaescalera.R;
import com.software.dafepa.proyectolaescalera.Singletones.AplicacionManager;

/*TODO COSAS QUE HACER AJAJ LOL
* Marcar un número de teléfono
* Abrir una localización GPS
* Mostrar notificaciones en Android mediante NotificacionManager
* Traducción a varios idiomas
* Visualización en diferentes dispositivos(tablet/movil)
* Persistencia de datos de la app.
* Añadir imágenes a Firebase cloud Storage
* Swipe Refresh Layout para actualizar contenidos
* Realización de pruebas y documentación
* Subir app a Play Store
* */

public class SplashActivity extends AppCompatActivity {

    //Interfaz
    private LinearLayout ly_logo_contenedor;
    private LinearLayout ly_menu;
    private TextView img_titulo;
    private TextView tvRegistrarse2;
    private Button btn_entrar2;
    private EditText edtxt_usuario;
    private EditText edtxt_pass;


    //Si es verdadero sacará el menu de login en pantalla, si no pasará a PantallaPrincipal
    private boolean show_menu = true;

    //variables para las animaciones
    private int desplazamiento_y;

    //Copia de activity para su fácil uso
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;
        HalpFuncs.translucentStatusBar(activity);

        HalpFuncs.init(activity);

        ly_logo_contenedor = (LinearLayout) findViewById(R.id.ly_logo_contenedor);
        ly_menu = (LinearLayout) findViewById(R.id.ly_menu);
        img_titulo = (TextView) findViewById(R.id.img_titulo);
        tvRegistrarse2 = (TextView) findViewById(R.id.tvRegistrarse2);
        btn_entrar2 = (Button) findViewById(R.id.btn_entrar2);
        edtxt_usuario = findViewById(R.id.edtxt_usuario);
        edtxt_pass = findViewById(R.id.edtxt_pass);

        //oculta el menu de login
        ly_menu.setVisibility(View.GONE);

        //Setea la fuente
        img_titulo.setTypeface(HalpFuncs.getFontHalpMe());


        animacion();

        tvRegistrarse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, RegistroActivity.class);
                startActivity(intent);
            }
        });

        btn_entrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loguearUsuario();
            }
        });





    }


    //animación de la pantalla
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

                        //TODO Comprobación si el usuario está logeado
                        if (show_menu) {
                            AnimatorSet set = new AnimatorSet();
                            set.playTogether(
                                    Glider.glide(Skill.CubicEaseOut, 2000,
                                            ObjectAnimator.ofFloat(ly_logo_contenedor, "translationY", 0, desplazamiento_y))

                            );

                            set.setDuration(duracion_desplazamiento);
                            set.start();
                            showMenu();
                        }else{
                            //TODO iniciar PantallaPrincipal
                        }
                    }
                }.start();

            }
        }.start();
    }

    //Función de la animación del menu de login
    private void showMenu(){
        ly_menu.setVisibility(View.VISIBLE);
        ly_menu.setAlpha(0);
        final int duracion_fade = 500;
        YoYo.with(Techniques.FadeIn).duration(duracion_fade).repeat(0).playOn(ly_menu);
    }

    private void loguearUsuario(){
        if (edtxt_usuario.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Debes introducir tu nombre de usuario para iniciar sesión!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_usuario.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if(edtxt_pass.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Debes introducir tu contraseña para iniciar sesión!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_pass.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if(!HalpFuncs.isOnline(activity)){
            new AlertDialog.Builder(activity).setMessage("¡Parece que no tienes internet, " +
                    "comprueba tu conexión por favor!")
                    .setPositiveButton("Aceptar", null).show();
        }else{
            String usu = edtxt_usuario.getText().toString();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference user_ref = database.getReference("halpme/usuarios");
            user_ref.orderByChild("nick").equalTo(usu).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    System.out.println(dataSnapshot.getKey());
                    String str = dataSnapshot.child("contrasena").getValue().toString();
                    if(str.equals(edtxt_pass.getText().toString())){
                        Intent intent = new Intent(activity, PantallaPrincipal.class);
                        startActivity(intent);
                        finish();
                    }else{
                        new AlertDialog.Builder(activity).setMessage("¡El nombre de usuario o contraseña son incorrectos!\n"
                        + str + "\n" + edtxt_pass.getText().toString())
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        edtxt_usuario.requestFocus();
                                        HalpFuncs.showKeyboard(activity);
                                    }
                                }).show();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}
