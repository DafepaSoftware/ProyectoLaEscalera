package com.software.dafepa.proyectolaescalera.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.software.dafepa.proyectolaescalera.HalpFuncs;
import com.software.dafepa.proyectolaescalera.PantallaPrincipal;
import com.software.dafepa.proyectolaescalera.R;

public class RegistroActivity extends AppCompatActivity {

    //interfaz
    private Button btn_registrarse;
    private EditText usuario;
    private EditText mail;
    private EditText contrasena1;
    private EditText contrasena2;

    //Copia de activity para su fácil uso
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        activity = this;
        HalpFuncs.translucentStatusBar(activity);

        btn_registrarse =  (Button) findViewById(R.id.btn_registrame);
        contrasena1 = (EditText) findViewById(R.id.et_contrasena1);

        //TODO hacer funcionalidad
        /*btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //comprobar que hay datos en contraseña
                if(contrasena1.length()!=contrasena2.length()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Las contraseñas deben coincidir", Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Registro realizado con exito!", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent e = new Intent(RegistroActivity.this, PantallaPrincipal.class);
                    startActivity(e);
                    finish();
                }

            }
        });*/

    }

    //Botón atrás de Android
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
