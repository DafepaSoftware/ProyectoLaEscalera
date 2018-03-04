package com.software.dafepa.proyectolaescalera;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.software.dafepa.proyectolaescalera.Objects.Evento;
import com.software.dafepa.proyectolaescalera.Singletones.AplicacionManager;

public class InfoElemeneto extends AppCompatActivity {

    private Toolbar myToolbar;
    private Button btnCancelar;
    private Activity activity;
    private Button btnLlamar;
    private Button btnLocalizar;
    private String numTelefono;
    private Uri  coordenadas;

    //Contiene toda la info del evento;
    private Evento evento;

    private TextView txt_descripcion;
    private TextView txt_titulo;
    private TextView txt_usuario;
    private TextView txt_titulo_titulo;
    private TextView txt_titulo_usuario;
    private ImageView imagenUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_elemeneto);


        evento = AplicacionManager.getInstance().getEvento();

        imagenUsuario = findViewById(R.id.imagenUsuario);
        txt_titulo_titulo = findViewById(R.id.titulo_titulo);
        txt_titulo_usuario = findViewById(R.id.titulo_usuario);
        txt_titulo = findViewById(R.id.txt_titulo);
        txt_descripcion = findViewById(R.id.txt_descripcion);

        //Esto peta de momento puesto que no tenemos imagenes en el usuario subidas al firebase

        //imagenUsuario.setImageDrawable(evento.getImg());
        txt_titulo_titulo.setText(evento.getTitulo());
        txt_titulo_usuario.setText(evento.getNick_usuario());
        txt_titulo.setText(evento.getTitulo());
        txt_descripcion.setText(evento.getDescripcion());


        btnLlamar = (Button) findViewById(R.id.btn_llamar);
        btnLocalizar = (Button) findViewById(R.id.btn_localizar);


        btnLocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordenadas = Uri.parse("geo:40.350033, -3.833532");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, coordenadas);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

            }
        });

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numTelefono = "+34666777888";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numTelefono, null));
                startActivity(intent);
            }
        });






        toolbarCode();


    }
    // Esto carga la toolbar
    private void toolbarCode(){
        myToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        myToolbar.setElevation(0.0f);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                crearEvento();
                return true;
            }
            case R.id.toolbar_cancel: {
                cancelarEvento();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void crearEvento(){
        finish();
    }

    private void cancelarEvento(){
        new AlertDialog.Builder(activity).setMessage("¿Deseas salir?\n\nLos datos introducidos no " +
                "se enviarán").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("No", null).show();
    }
}

