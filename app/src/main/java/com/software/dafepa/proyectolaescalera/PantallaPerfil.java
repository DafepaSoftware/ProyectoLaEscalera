package com.software.dafepa.proyectolaescalera;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;

public class PantallaPerfil extends AppCompatActivity {

    private Toolbar myToolbar;
    private Button editar;
    private Button editarNombre;
    private Button editarCorreo;
    private EditText textNombre;
    private EditText textCorreo;
    private Activity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_perfil);

        editar = (Button) findViewById(R.id.btn_editar);
        editarNombre = (Button) findViewById(R.id.btn_editarNombre);
        editarCorreo = (Button) findViewById(R.id.btn_editarEmail);
        textNombre = (EditText) findViewById(R.id.textNombre);
        textCorreo = (EditText) findViewById(R.id.textCorreo);

        activity = this;




        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallaeditar = new Intent(PantallaPerfil.this,EditUser.class );
                startActivity(pantallaeditar);
            }

        });

        editarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textNombre.setEnabled(true);
            }
        });

        editarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCorreo.setEnabled(true);
            }
        });
        toolbarCode();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void toolbarCode(){
        myToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        myToolbar.setElevation(0.0f);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_nuevoelemento, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            case R.id.toolbar_acept: {
                guardarCambios();
                return  true;

            }
            case R.id.toolbar_cancel: {
                cancelarEvento();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void guardarCambios(){
        finish();
    }
    private void cancelarEvento(){
        new AlertDialog.Builder(activity).setMessage("¿Deseas cancelar?\n\nLos datos introducidos no " +
                "se enviarán").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("No", null).show();
    }

}
