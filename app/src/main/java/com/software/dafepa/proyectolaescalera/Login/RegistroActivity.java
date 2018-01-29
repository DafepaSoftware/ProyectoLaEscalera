package com.software.dafepa.proyectolaescalera.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        btn_registrarse =  (Button) findViewById(R.id.btn_registrame);
        contrasena1 = (EditText) findViewById(R.id.et_contrasena1);
        contrasena2 = (EditText) findViewById(R.id.et_contrasena2);

        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });

    }
}
