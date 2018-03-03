package com.software.dafepa.proyectolaescalera;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.software.dafepa.proyectolaescalera.Objects.Evento;
import com.software.dafepa.proyectolaescalera.Utilidades.ApplicationData;
import com.software.dafepa.proyectolaescalera.Utilidades.HalpFuncs;
import com.software.dafepa.proyectolaescalera.Utilidades.ZoomImageActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//Todo revisar el tema de la ampliación de la imagen
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
    private Button btn_aceptar;

    private LinearLayout ly_galeria;
    private LinearLayout ly_camara;
    private LinearLayout ly_imagen;
    private ImageView img_evento;

    private EditText edtxt_telefono;

    private String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RESULT_LOAD_IMG = 2;

    private Uri imageuri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento);
        activity = this;

        toolbarCode();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtxt_titulo_ = findViewById(R.id.edtxt_titulo);
        txt_caracteres_ = findViewById(R.id.txt_caracteres);
        edtxt_telefono = findViewById(R.id.edtxt_telefono);

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



        ly_galeria = findViewById(R.id.ly_galeria);
        ly_camara = findViewById(R.id.ly_camara);
        ly_imagen = findViewById(R.id.ly_imagen);
        img_evento = findViewById(R.id.img_evento);

        ly_galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galeria();
            }
        });

        ly_camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camara();
            }
        });

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
        if (edtxt_titulo_.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Necesitamos saber brevemente qué necesitas!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_titulo_.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if(edtxt_descripcion_.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Necesitamos una descripción detallada de lo que necesitas!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_descripcion_.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if(edtxt_telefono.getText().toString().length()<9){
            new AlertDialog.Builder(activity).setMessage("¡Necesitamos un número de teléfono válido!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_telefono.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if(ly_imagen.getVisibility() == View.GONE){
            new AlertDialog.Builder(activity).setMessage("¡Necesitamos una imagen!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_telefono.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if(!HalpFuncs.isOnline(activity)){
            new AlertDialog.Builder(activity).setMessage("¡Parece que no tienes internet, " +
                    "comprueba tu conexión por favor!")
                    .setPositiveButton("Aceptar", null).show();
        }else{



            ApplicationData appdata = new ApplicationData();
            appdata.cargarAplicacionDePreferencias(activity);

            Evento evento = new Evento();
            evento.setTitulo(edtxt_titulo_.getText().toString());
            evento.setDescripcion(edtxt_descripcion_.getText().toString());
            evento.setTfno(Integer.parseInt(edtxt_telefono.getText().toString()));
            evento.setBusco(busco);
            evento.setNick_usuario(appdata.getUser().getNick());

            //TODO hacer que autoincremente
            evento.setID(1);


            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("halpme/eventos");
            DatabaseReference usersRef = ref.child(String.valueOf(evento.getID()));

            /*StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference ref2 = storageReference.child("images/"+ evento.getID());
            ref2.putFile(img_evento)*/

            usersRef.setValue(evento);

            new AlertDialog.Builder(activity).setMessage("¡Tu petición se ha creado satisfactoriamente!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
        }

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



    private void camara()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = Uri.fromFile(photoFile);/*FileProvider.getUriForFile(a,
                            BuildConfig.APPLICATION_ID,
                            photoFile);*/
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            File image = new File(mCurrentPhotoPath);
            Bitmap myBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());

            ExifInterface ei = null;
            try {
                ei = new ExifInterface(image.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap rotatedBitmap = null;
            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(myBitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(myBitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(myBitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = myBitmap;
            }




            img_evento.setImageBitmap(rotatedBitmap);
            ly_imagen.setVisibility(View.VISIBLE);


        }
        if ( requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                img_evento.setImageBitmap(selectedImage);
                ly_imagen.setVisibility(View.VISIBLE);

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }

        }
        if(requestCode==234) //Aqui es cuando no ha seleccionado archivo...
        {

        }

    }

    private void galeria()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }
}
