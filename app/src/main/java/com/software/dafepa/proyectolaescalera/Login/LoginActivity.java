package com.software.dafepa.proyectolaescalera.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.software.dafepa.proyectolaescalera.PantallaPrincipal;
import com.software.dafepa.proyectolaescalera.R;

import org.w3c.dom.Text;
/**
 *
 *
 *
 *  ESTO YA NO VALE AJAJ
 *
 *
 *
 * **/
public class LoginActivity extends AppCompatActivity {

    Button btn_entrar;
    TextView registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registrarse = (TextView) findViewById(R.id.tvRegistrarse);
        btn_entrar = (Button) findViewById(R.id.btn_entrar);

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, PantallaPrincipal.class);
                startActivity(i);
                finish();
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(r);
                finish();
            }
        });
    }
}
