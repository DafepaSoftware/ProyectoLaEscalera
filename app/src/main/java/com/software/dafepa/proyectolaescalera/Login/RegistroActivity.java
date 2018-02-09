package com.software.dafepa.proyectolaescalera.Login;

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

import com.software.dafepa.proyectolaescalera.PantallaPrincipal;
import com.software.dafepa.proyectolaescalera.R;

public class RegistroActivity extends AppCompatActivity {

    Button btn_registrarse;
    EditText usuario;
    EditText mail;
    EditText contrasena1;
    EditText contrasena2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        translucentStatusBar();

        btn_registrarse =  (Button) findViewById(R.id.btn_registrame);
        contrasena1 = (EditText) findViewById(R.id.et_contrasena1);

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
    private void translucentStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
