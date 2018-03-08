package com.software.dafepa.proyectolaescalera;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.software.dafepa.proyectolaescalera.Objects.Usuario;
import com.software.dafepa.proyectolaescalera.Singletones.AplicacionManager;
import com.software.dafepa.proyectolaescalera.Utilidades.ApplicationData;

public class EditUser extends AppCompatActivity {


    private static final int PICK_IMAGE = 100;

    private Usuario usuario;

    private CircularImageView fotoperfil;
    private EditText et_nombre;
    private EditText et_apellidos;
    private EditText et_correo;
    private Button btn_guardar;
    private Button btn_fecha;


    

    Uri imageUri;
    CircularImageView foto_gallery;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_perfil);

        ApplicationData appdata = new ApplicationData();
        appdata.cargarAplicacionDePreferencias(this);
        Usuario usr = AplicacionManager.getInstance().getUsuario();

        TextView usu = findViewById(R.id.txt_nombre);

        usuario = AplicacionManager.getInstance().getUsuario();

        btn_guardar = (Button) findViewById(R.id.btn_guardar);

        et_nombre = (EditText) findViewById(R.id.edtxt_nombre);
        et_apellidos = (EditText) findViewById(R.id.edtxt_apellido);
        et_correo = (EditText) findViewById(R.id.edtxt_correo);
        btn_fecha = findViewById(R.id.btn_fecha);



        usu.setText(usr.getNick());
        et_nombre.setHint(usr.getNombre());
        et_apellidos.setHint(usr.getApellido());
        et_correo.setHint(usr.getMail());


        foto_gallery = (CircularImageView) findViewById(R.id.imagenUsuario);

        foto_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        toolbarCode();

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios(usuario);
                comprobarCampos();
            }
        });
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            foto_gallery.setImageURI(imageUri);
        }
    }
    private void toolbarCode(){
        myToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        myToolbar.setElevation(0.0f);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void guardarCambios(Usuario u){
        ApplicationData appdata = new ApplicationData();
        appdata.cargarAplicacionDePreferencias(this);
        //appdata.setUser(u);
        appdata.guardarEnPreferencias(this);
    }

    private void comprobarCampos(){
        subirFirebase();
    }
    private void subirFirebase(){

    }
}