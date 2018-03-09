package com.software.dafepa.proyectolaescalera;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.software.dafepa.proyectolaescalera.Adapters.ArrayAdapterWithIcon;
import com.software.dafepa.proyectolaescalera.Objects.Usuario;
import com.software.dafepa.proyectolaescalera.Singletones.AplicacionManager;
import com.software.dafepa.proyectolaescalera.Utilidades.ApplicationData;
import com.software.dafepa.proyectolaescalera.Utilidades.HalpFuncs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

public class EditUser extends AppCompatActivity {


    private static final int PICK_IMAGE = 100;

    private Usuario usuario;


    private EditText et_nombre;
    private EditText et_apellidos;
    private EditText et_correo;
    private Button btn_guardar;
    private Button btn_fecha;

    private Activity activity;
    private EditText edtxt_pass;
    private EditText edtxt_pass2;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RESULT_LOAD_IMG = 2;

    private String mCurrentPhotoPath;
    private ProgressBar progressBar;
    private RelativeLayout ly_main;


    private Uri imageuri = null;
    private CircularImageView foto_gallery;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_perfil);
        activity = this;

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
        edtxt_pass = findViewById(R.id.edtxt_pass);
        edtxt_pass2= findViewById(R.id.edtxt_pass2);
        ly_main = findViewById(R.id.ly_main);



        usu.setText(usr.getNick());
        et_nombre.setText(usr.getNombre());
        et_apellidos.setText(usr.getApellido());
        et_correo.setText(usr.getMail());
        btn_fecha.setText(usr.getFecha_naci());
        edtxt_pass.setText(usr.getContrasena());
        edtxt_pass2.setText(usr.getContrasena());


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

        foto_gallery = (CircularImageView) findViewById(R.id.imagenUsuario);
        foto_gallery.setImageDrawable(new BitmapDrawable(getResources(), usr.getImg()));

        foto_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuSeleccionTipoArchivo();
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


    private void toolbarCode(){
        myToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        myToolbar.setElevation(0.0f);
        myToolbar.setTitle("Editar usuario");
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = new Intent(activity, PantallaPrincipal.class);
                startActivity(intent);
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void guardarCambios(Usuario u){
        ApplicationData appdata = new ApplicationData();

        appdata.guardarEnPreferencias(this);
    }

    private void menuSeleccionTipoArchivo(){
        final String [] items = new String[] {getResources().getString(R.string.galeria),
                getResources().getString(R.string.camara)};
        final Integer[] icons = new Integer[] {R.drawable.gallery_logo_small, R.drawable.camera_icon_small};
        ListAdapter adapter = new ArrayAdapterWithIcon(activity, items, icons);

        new AlertDialog.Builder(activity).setTitle("Selecciona una imagen de perfil")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item ) {
                        switch (item){
                            case 0:{
                                galeria();
                                break;
                            }
                            case 1:{
                                camara();
                                break;
                            }
                        }}
                }).setPositiveButton("Cancelar", null).show();
    }

    private void galeria()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
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




            foto_gallery.setImageBitmap(rotatedBitmap);

            imageuri = Uri.fromFile(image);


        }
        if ( requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                foto_gallery.setImageBitmap(selectedImage);
                imageuri = imageUri;

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }

        }
        if(requestCode==234) //Aqui es cuando no ha seleccionado archivo...
        {

        }

    }

    private void comprobarCampos(){


        boolean contrasena_espacios = false;
        for (int i = 0; i < edtxt_pass.getText().toString().length(); ++i){
            if (edtxt_pass.getText().toString().toCharArray()[i] == ' '){
                contrasena_espacios = true;
                i = edtxt_pass.getText().toString().length();
            }
        }

        if (et_nombre.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Necisitamos conocer cómo te llamas!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            et_nombre.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();

        }else if (et_apellidos.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Necisitamos conocer al menos uno de tus " +
                    "apellidos!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            et_apellidos.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();

        }else if (et_correo.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Necesitamos tener tu correo de contacto!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            et_correo.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();
        }else if(!HalpFuncs.validateEmail(et_correo.getText().toString())){
            new AlertDialog.Builder(activity).setMessage("¡Parece que no es un correo electrónico válido!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            et_correo.requestFocus();
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

        }else if(!HalpFuncs.isOnline(activity)){
            new AlertDialog.Builder(activity).setMessage("¡Parece que no tienes internet, " +
                    "comprueba tu conexión por favor!")
                    .setPositiveButton("Aceptar", null).show();
        }else{
            subirFirebase();
        }

    }
    private void subirFirebase(){
        progressBar = new ProgressBar(activity,null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300,300);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        ly_main.addView(progressBar,params);
        progressBar.setBackgroundColor(Color.parseColor("#33333333"));
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        final String nick = AplicacionManager.getInstance().getUsuario().getNick();

        if(imageuri == null){
            progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            Usuario u = new Usuario();
            u.setNick(nick);
            u.setNombre(et_nombre.getText().toString());
            u.setApellido(et_apellidos.getText().toString());
            u.setContrasena(edtxt_pass.getText().toString());
            u.setMail(et_correo.getText().toString());
            u.setFecha_naci(btn_fecha.getText().toString());


            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("halpme/usuarios");
            DatabaseReference usersRef = ref.child(u.getNick());

            AplicacionManager.getInstance().getUsuario().setNombre(u.getNombre());
            AplicacionManager.getInstance().getUsuario().setApellido(u.getApellido());
            AplicacionManager.getInstance().getUsuario().setContrasena(u.getContrasena());
            AplicacionManager.getInstance().getUsuario().setMail(u.getMail());
            AplicacionManager.getInstance().getUsuario().setFecha_naci(u.getFecha_naci());
            usersRef.setValue(u);

            new AlertDialog.Builder(activity).setMessage("¡Tu usuario se ha creado satisfactoriamente!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(EditUser.this, PantallaPrincipal.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();

        }else {

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference ref2 = storageReference.child("images/usuarios/" + nick);
            ref2.putFile(imageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            Usuario u = new Usuario();
                            u.setNick(nick);
                            u.setNombre(et_nombre.getText().toString());
                            u.setApellido(et_apellidos.getText().toString());
                            u.setContrasena(edtxt_pass.getText().toString());
                            u.setMail(et_correo.getText().toString());
                            u.setFecha_naci(btn_fecha.getText().toString());


                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("halpme/usuarios");
                            DatabaseReference usersRef = ref.child(u.getNick());

                            AplicacionManager.getInstance().getUsuario().setNombre(u.getNombre());
                            AplicacionManager.getInstance().getUsuario().setApellido(u.getApellido());
                            AplicacionManager.getInstance().getUsuario().setContrasena(u.getContrasena());
                            AplicacionManager.getInstance().getUsuario().setMail(u.getMail());
                            AplicacionManager.getInstance().getUsuario().setFecha_naci(u.getFecha_naci());
                            usersRef.setValue(u);

                            new AlertDialog.Builder(activity).setMessage("¡Tu usuario se ha creado satisfactoriamente!")
                                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(EditUser.this, PantallaPrincipal.class);
                                            startActivity(intent);
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
    public void onBackPressed() {
        Intent intent = new Intent(activity, PantallaPrincipal.class);
        startActivity(intent);
        finish();
    }
}