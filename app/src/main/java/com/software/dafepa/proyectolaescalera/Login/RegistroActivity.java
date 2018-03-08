package com.software.dafepa.proyectolaescalera.Login;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.software.dafepa.proyectolaescalera.Objects.Evento;
import com.software.dafepa.proyectolaescalera.Utilidades.ApplicationData;
import com.software.dafepa.proyectolaescalera.Utilidades.HalpFuncs;
import com.software.dafepa.proyectolaescalera.Objects.Usuario;
import com.software.dafepa.proyectolaescalera.R;
import com.software.dafepa.proyectolaescalera.Utilidades.ZoomImageActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

public class RegistroActivity extends AppCompatActivity {

    private Toolbar myToolbar;

    //interfaz
    private Button btn_registrarse;
    private EditText edtxt_nombre;
    private EditText edtxt_apellido;
    private EditText edtxt_correo;
    private EditText edtxt_pass;
    private EditText edtxt_pass2;
    private EditText edtxt_nick;
    private Button btn_fecha;

    private LinearLayout ly_galeria;
    private LinearLayout ly_camara;
    private LinearLayout ly_imagen;
    private ImageView img;
    private Uri imageuri;
    private String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RESULT_LOAD_IMG = 2;

    private ProgressBar progressBar;
    private boolean uploaded = false;
    private RelativeLayout ly_main;

    //Copia de activity para su fácil uso
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        activity = this;
        //HalpFuncs.translucentStatusBar(activity);
        toolbarCode();

        edtxt_nombre = findViewById(R.id.edtxt_nombre);
        edtxt_apellido = findViewById(R.id.edtxt_apellido);
        edtxt_correo = findViewById(R.id.edtxt_correo);
        edtxt_pass = (EditText) findViewById(R.id.edtxt_pass);
        edtxt_pass2 = (EditText) findViewById(R.id.edtxt_pass2);
        btn_registrarse =  (Button) findViewById(R.id.btn_registrame);
        edtxt_nick = findViewById(R.id.edtxt_nick);
        btn_fecha = (Button) findViewById(R.id.btn_fecha);
        ly_main = findViewById(R.id.ly_main);

        (findViewById(R.id.btn_terminos)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminosyCondiciones();
            }
        });


        //Funcionalidad calendario
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // myCalendar.add(Calendar.DATE, 0);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                //textView.setText(sdf.format(myCalendar.getTime()));
            }


        };

        btn_fecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox

                                if (year < mYear)
                                    view.updateDate(mYear,mMonth,mDay);

                                if (monthOfYear < mMonth && year == mYear)
                                    view.updateDate(mYear,mMonth,mDay);

                                if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                    view.updateDate(mYear,mMonth,mDay);
                                String day = "";
                                String month = "";
                                if(dayOfMonth < 10){
                                    day = "0"+dayOfMonth;
                                }else{
                                    day = Integer.toString(dayOfMonth);
                                }
                                if((monthOfYear+1) < 10){
                                    month = "0"+(monthOfYear+1);
                                }else{
                                    month = Integer.toString(monthOfYear+1);
                                }

                                btn_fecha.setText(day + "/"
                                        + month + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                //dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();
            }
        });

        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobacionesCampos();

            }
        });



        ly_galeria = findViewById(R.id.ly_galeria);
        ly_camara = findViewById(R.id.ly_camara);
        ly_imagen = findViewById(R.id.ly_imagen);
        img = findViewById(R.id.img);

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

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.buildDrawingCache();

                //Obtenemos el bitmap
                Bitmap bmap = img.getDrawingCache();

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                cancelarRegistro();
                return true;
            }
            case R.id.toolbar_terminos: {
                terminosyCondiciones();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //Botón atrás de Android
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelarRegistro();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar_registro, menu);
        return true;
    }

    private void comprobacionesCampos(){

        boolean usuario_espacio = false;
        for (int i = 0; i < edtxt_nick.getText().toString().length(); ++i){
            if (edtxt_nick.getText().toString().toCharArray()[i] == ' '){
                usuario_espacio = true;
                i = edtxt_nick.getText().toString().length();
            }
        }

        boolean contrasena_espacios = false;
        for (int i = 0; i < edtxt_pass.getText().toString().length(); ++i){
            if (edtxt_pass.getText().toString().toCharArray()[i] == ' '){
                contrasena_espacios = true;
                i = edtxt_pass.getText().toString().length();
            }
        }

        if (edtxt_nick.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Necesitas tener un nombre de ususario!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_nick.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if (usuario_espacio){
            new AlertDialog.Builder(activity).setMessage("¡Tu nick no puede contener espacios!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_nick.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if (edtxt_nombre.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Necisitamos conocer cómo te llamas!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_nombre.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();

        }else if (edtxt_apellido.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Necisitamos conocer al menos uno de tus " +
                    "apellidos!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_apellido.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();

        }else if (edtxt_correo.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Necesitamos tener tu correo de contacto!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_correo.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if(!HalpFuncs.validateEmail(edtxt_correo.getText().toString())){
            new AlertDialog.Builder(activity).setMessage("¡Parece que no es un correo electrónico válido!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_correo.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if(btn_fecha.getText().toString().equals("FECHA DE NACIMIENTO")){
            new AlertDialog.Builder(activity).setMessage("¡Necisitamos conocer cuando nacistes!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            btn_fecha.requestFocus();
                        }
                    }).show();
        }else if (edtxt_pass.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Necesitas una contraseña para tu cuenta!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_pass.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if(edtxt_pass.getText().toString().length() <= 3){
            new AlertDialog.Builder(activity).setMessage("¡Tu contraseña debe contener al menos cuatro caracteres!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_pass.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if(contrasena_espacios){
            new AlertDialog.Builder(activity).setMessage("¡Tu contraseña no puede contener espacios!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_pass.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if (!edtxt_pass.getText().toString().equals(edtxt_pass2.getText().toString())){
            new AlertDialog.Builder(activity).setMessage("¡Las contraseñas deben ser iguales!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_pass.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();

        }else if(ly_imagen.getVisibility() == View.GONE){
            new AlertDialog.Builder(activity).setMessage("¡Necesitamos una imagen!")
                    .setPositiveButton("Aceptar", null).show();
        }else if(!HalpFuncs.isOnline(activity)){
            new AlertDialog.Builder(activity).setMessage("¡Parece que no tienes internet, " +
                    "comprueba tu conexión por favor!")
                    .setPositiveButton("Aceptar", null).show();
        }else{
            crearUsuario();
        }
    }

    private void crearUsuario(){
        progressBar = new ProgressBar(activity,null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300,300);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        ly_main.addView(progressBar,params);
        progressBar.setBackgroundColor(Color.parseColor("#33333333"));
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);



        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference ref2 = storageReference.child("images/usuarios/"+ edtxt_nick.getText().toString());
        ref2.putFile(imageuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        ApplicationData appdata = new ApplicationData();
                        appdata.cargarAplicacionDePreferencias(activity);

                        Usuario u = new Usuario();
                        u.setNombre(edtxt_nombre.getText().toString());
                        u.setApellido(edtxt_apellido.getText().toString());
                        u.setContrasena(edtxt_pass.getText().toString());
                        u.setMail(edtxt_correo.getText().toString());
                        u.setNick(edtxt_nick.getText().toString());
                        u.setFecha_naci(btn_fecha.getText().toString());

                        //TODO comprobar si el usuario existe
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("halpme/usuarios");
                        DatabaseReference usersRef = ref.child(u.getNick());


                        usersRef.setValue(u);

                        new AlertDialog.Builder(activity).setMessage("¡Tu usuario se ha creado satisfactoriamente!")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                }).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new AlertDialog.Builder(activity).setMessage("¡Ha habido un problema con tu petición, intentalo otra vez!")
                        .setPositiveButton("Aceptar", null).show();
            }
        });






    }

    private void cancelarRegistro(){
        new AlertDialog.Builder(activity).setMessage("¿Deseas salir?\n\nLos datos introducidos no " +
                "se enviarán").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("No", null).show();
    }

    private void terminosyCondiciones(){
        new AlertDialog.Builder(activity).setMessage(getResources().getString(R.string.terminos_condiciones)).setPositiveButton("Aceptar", null).show();
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
        String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
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




            img.setImageBitmap(rotatedBitmap);
            ly_imagen.setVisibility(View.VISIBLE);

            imageuri = Uri.fromFile(image);


        }
        if ( requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                img.setImageBitmap(selectedImage);
                ly_imagen.setVisibility(View.VISIBLE);
                imageuri = imageUri;

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
