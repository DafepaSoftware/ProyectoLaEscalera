package com.software.dafepa.proyectolaescalera.Login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.software.dafepa.proyectolaescalera.Objects.Evento;
import com.software.dafepa.proyectolaescalera.Objects.Usuario;
import com.software.dafepa.proyectolaescalera.Utilidades.ApplicationData;
import com.software.dafepa.proyectolaescalera.Utilidades.HalpFuncs;
import com.software.dafepa.proyectolaescalera.PantallaPrincipal;
import com.software.dafepa.proyectolaescalera.R;
import com.software.dafepa.proyectolaescalera.Singletones.AplicacionManager;

import java.io.File;
import java.io.IOException;
import java.util.Random;

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
    private RelativeLayout ly_main;
    private TextView img_titulo;
    private TextView tvRegistrarse2;
    private Button btn_entrar2;
    private EditText edtxt_usuario;
    private EditText edtxt_pass;

    private ProgressBar progressBar;
    private CheckBox cbx_recuerdame;


    //Si es verdadero sacará el menu de login en pantalla, si no pasará a PantallaPrincipal
    private boolean show_menu = true;

    //variables para las animaciones
    private int desplazamiento_y;

    //Copia de activity para su fácil uso
    private Activity activity;

    private ApplicationData appdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;
        HalpFuncs.translucentStatusBar(activity);

        HalpFuncs.init(activity);

        appdata = new ApplicationData();
        appdata.cargarAplicacionDePreferencias(activity);
        if(appdata.isRememberme()){
            show_menu = false;
        }

        ly_logo_contenedor = (LinearLayout) findViewById(R.id.ly_logo_contenedor);
        ly_menu = (LinearLayout) findViewById(R.id.ly_menu);
        img_titulo = (TextView) findViewById(R.id.img_titulo);
        tvRegistrarse2 = (TextView) findViewById(R.id.tvRegistrarse2);
        btn_entrar2 = (Button) findViewById(R.id.btn_entrar2);
        edtxt_usuario = findViewById(R.id.edtxt_usuario);
        edtxt_pass = findViewById(R.id.edtxt_pass);
        ly_main = findViewById(R.id.ly_main);
        cbx_recuerdame = findViewById(R.id.cbx_recuerdame);

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
                            usuarioRecuerdame();
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
            //progressBar
            progressBar = new ProgressBar(activity,null,android.R.attr.progressBarStyleLarge);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300,300);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            ly_main.addView(progressBar,params);
            progressBar.setBackgroundColor(Color.parseColor("#33333333"));
            progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            final String usu = edtxt_usuario.getText().toString();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference user_ref = database.getReference("halpme/usuarios");
            user_ref.orderByChild("nick").equalTo(usu).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    final Usuario u = dataSnapshot.getValue(Usuario.class);


                    if(u.getContrasena().equals(edtxt_pass.getText().toString())){
                        final File localFile;
                        try {
                            Random r = new Random();
                            String path = "tmp" + r.nextInt(999999) + "_" + usu;
                            localFile = File.createTempFile(path, "jpg");

                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            storage.getReference("images/usuarios/" + usu).
                                    getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    progressBar.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    u.setImg(bitmap);
                                    Intent intent = new Intent(activity, PantallaPrincipal.class);
                                    startActivity(intent);

                                    appdata.setRememberme(cbx_recuerdame.isChecked());
                                    appdata.setUsuario_nick(u.getNick());
                                    appdata.guardarEnPreferencias(activity);
                                    AplicacionManager.getInstance().setUsuario(u);

                                    finish();
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }else{
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        new AlertDialog.Builder(activity).setMessage("¡El nombre de usuario o contraseña son incorrectos!")
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
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    new AlertDialog.Builder(activity).setMessage("¡El nombre de usuario o contraseña son incorrectos!")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    edtxt_usuario.requestFocus();
                                    HalpFuncs.showKeyboard(activity);
                                }
                            }).show();
                }

            });
        }


    }

    private void usuarioRecuerdame(){
        //progressBar
        progressBar = new ProgressBar(activity,null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300,300);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        ly_main.addView(progressBar,params);
        progressBar.setBackgroundColor(Color.parseColor("#33333333"));
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        final String usu = appdata.getUsuario_nick();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference user_ref = database.getReference("halpme/usuarios");
        user_ref.orderByChild("nick").equalTo(usu).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Usuario u = dataSnapshot.getValue(Usuario.class);
                    final File localFile;
                    try {
                        Random r = new Random();
                        String path = "tmp" + r.nextInt(999999) + "_" + usu;
                        localFile = File.createTempFile(path, "jpg");

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        storage.getReference("images/usuarios/" + usu).
                                getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                progressBar.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                u.setImg(bitmap);
                                Intent intent = new Intent(activity, PantallaPrincipal.class);
                                startActivity(intent);

                                appdata.setRememberme(cbx_recuerdame.isChecked());
                                appdata.setUsuario_nick(u.getNick());
                                appdata.guardarEnPreferencias(activity);
                                AplicacionManager.getInstance().setUsuario(u);

                                finish();
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
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
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                new AlertDialog.Builder(activity).setMessage("¡El nombre de usuario o contraseña son incorrectos!")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                edtxt_usuario.requestFocus();
                                HalpFuncs.showKeyboard(activity);
                            }
                        }).show();
            }
        });


}


}
