package com.software.dafepa.proyectolaescalera;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NuevoEvento extends AppCompatActivity {

    private Toolbar myToolbar;

    private EditText edtxt_titulo_;
    private EditText edtxt_descripcion_;
    private TextView txt_caracteres_;
    private TextView txt_caracteres2_;
    private String last_titulo_;
    private String last_descripcion_;
    private Activity activity;
    private boolean busco = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento);
        activity = this;
        toolbarCode();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edtxt_titulo_ = findViewById(R.id.edtxt_titulo);
        txt_caracteres_ = findViewById(R.id.txt_caracteres);

        edtxt_titulo_.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                last_titulo_ = edtxt_titulo_.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txt_title = edtxt_titulo_.getText().toString();
                if (txt_title.length() > 50){
                    edtxt_titulo_.setText(last_titulo_);
                }else {
                    String txtaux = txt_title.length() +
                            " / 50 caracteres";
                    txt_caracteres_.setText(txtaux);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtxt_descripcion_ = findViewById(R.id.edtxt_descripcion);
        txt_caracteres2_ = findViewById(R.id.txt_caracteres2);


        edtxt_descripcion_.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                last_descripcion_ = edtxt_descripcion_.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String txt_title = edtxt_descripcion_.getText().toString();
                if (txt_title.length() > 500){
                    edtxt_descripcion_.setText(last_descripcion_);
                }else {
                    String txtaux = txt_title.length() +
                            " / 500 caracteres";
                    txt_caracteres2_.setText(txtaux);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        (findViewById(R.id.btn_create)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearEvento();
            }
        });

        (findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelarEvento();
            }
        });

        final LinearLayout ly_busco = findViewById(R.id.ly_busco);
        final LinearLayout ly_necesito = findViewById(R.id.ly_necesito);

        ly_busco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ly_busco.setBackgroundColor(getColor(R.color.color_fondo_oscurito));
                (findViewById(R.id.ly_busco_rayica)).setBackgroundColor(getColor(R.color.colorAccent));
                ((TextView) findViewById(R.id.txt_busco)).setTextColor(getColor(R.color.texto_destacado));

                ly_necesito.setBackgroundColor(getColor(R.color.color_fondo));
                (findViewById(R.id.ly_necesito_rayica)).setBackgroundColor(getColor(R.color.color_fondo));
                ((TextView) findViewById(R.id.txt_necesito)).setTextColor(getColor(R.color.texto_normal));

                busco = true;
            }
        });


        ly_necesito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ly_busco.setBackgroundColor(getColor(R.color.color_fondo));
                (findViewById(R.id.ly_busco_rayica)).setBackgroundColor(getColor(R.color.color_fondo));
                ((TextView) findViewById(R.id.txt_busco)).setTextColor(getColor(R.color.texto_normal));

                ly_necesito.setBackgroundColor(getColor(R.color.color_fondo_oscurito));
                (findViewById(R.id.ly_necesito_rayica)).setBackgroundColor(getColor(R.color.colorAccent));
                ((TextView) findViewById(R.id.txt_necesito)).setTextColor(getColor(R.color.texto_destacado));


                busco = false;
            }
        });
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
