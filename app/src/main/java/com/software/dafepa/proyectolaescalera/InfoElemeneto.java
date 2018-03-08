package com.software.dafepa.proyectolaescalera;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.software.dafepa.proyectolaescalera.Utilidades.ZoomImageActivity;

import java.io.ByteArrayOutputStream;

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
    private TextView txt_tipo;
    private TextView txt_usuario;
    private TextView txt_titulo_titulo;
    private TextView txt_titulo_usuario;
    private ImageView imagenUsuario;
    private ImageView img_evento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_elemeneto);
        activity = this;


        evento = AplicacionManager.getInstance().getEvento();

        imagenUsuario = findViewById(R.id.imagenUsuario);
        txt_titulo_titulo = findViewById(R.id.titulo_titulo);
        txt_titulo_usuario = findViewById(R.id.titulo_usuario);
        txt_tipo = findViewById(R.id.txt_tipo);
        txt_descripcion = findViewById(R.id.txt_descripcion);
        img_evento = findViewById(R.id.img_evento);

        //Esto peta de momento puesto que no tenemos imagenes en el usuario subidas al firebase

        imagenUsuario.setImageDrawable(new BitmapDrawable(getResources(), evento.getImg_usuario()));
        txt_titulo_titulo.setText(evento.getTitulo());
        txt_titulo_usuario.setText(evento.getNick_usuario());
        if (evento.getBusco()){
            txt_tipo.setText("Busco");
        }else{
            txt_tipo.setText("Ofrezco");
        }
        txt_descripcion.setText(evento.getDescripcion());
        img_evento.setImageBitmap(evento.getImg());

        img_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_evento.buildDrawingCache();

                //Obtenemos el bitmap
                Bitmap bmap = img_evento.getDrawingCache();

                //Lo convertimos a array de bytes
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                byte[] byteArray = stream.toByteArray();

                //Lo pasamos como parametro a la otra activity de Zoom
                Intent mainIntent = new Intent().setClass(activity, ZoomImageActivity.class);
                mainIntent.putExtra("BitmapImage", byteArray);
                startActivity(mainIntent);
            }
        });

        btnLlamar = (Button) findViewById(R.id.btn_llamar);
        btnLocalizar = (Button) findViewById(R.id.btn_localizar);


        btnLocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Aqui el codigo postal
                int cod_postal= evento.getCodigo_postal();
                coordenadas = Uri.parse("geo:0,0?q="+cod_postal+"\n");
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

                //Aqui va el numero de telefono
                numTelefono = "+34" + evento.getTfno();
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

