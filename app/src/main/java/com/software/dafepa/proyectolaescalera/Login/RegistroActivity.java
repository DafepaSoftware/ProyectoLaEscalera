package com.software.dafepa.proyectolaescalera.Login;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.software.dafepa.proyectolaescalera.HalpFuncs;
import com.software.dafepa.proyectolaescalera.Objects.Usuario;
import com.software.dafepa.proyectolaescalera.PantallaPrincipal;
import com.software.dafepa.proyectolaescalera.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//TODO deberíamos limitar los campos de alguna manera
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
        if (edtxt_nick.getText().toString().length() <= 0){
            new AlertDialog.Builder(activity).setMessage("¡Necesitas tener un nombre de ususario!")
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
            new AlertDialog.Builder(activity).setMessage("¡Necisitamos conocer tu correo electrónico!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_correo.requestFocus();
                            HalpFuncs.showKeyboard(activity);
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

        }else if (!edtxt_pass.getText().toString().equals(edtxt_pass2.getText().toString())){
            new AlertDialog.Builder(activity).setMessage("¡Las contraseñas deben ser iguales!")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            edtxt_pass.requestFocus();
                            HalpFuncs.showKeyboard(activity);
                        }
                    }).show();

        }else{
            crearUsuario();
        }
    }

    private void crearUsuario(){
        Usuario u = new Usuario();
        u.setNombre(edtxt_nombre.getText().toString());
        u.setApellido(edtxt_apellido.getText().toString());
        u.setContrasena(edtxt_pass.getText().toString());
        u.setMail(edtxt_correo.getText().toString());
        u.setNick(edtxt_nick.getText().toString());

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
        new AlertDialog.Builder(activity).setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis ligula pharetra odio varius posuere sit amet non ligula. Donec eu efficitur nisi. Ut risus massa, tincidunt vel urna eget, gravida venenatis justo. Ut pulvinar porttitor mi non luctus. Nullam rutrum odio at purus fermentum dictum. In sagittis metus lectus, id volutpat quam venenatis at. Phasellus id tristique urna, sed fermentum felis. Suspendisse luctus, sapien nec volutpat condimentum, eros libero ultrices ex, nec porta urna lacus a sapien. Integer rutrum eu neque tincidunt pulvinar. Quisque consectetur, ex dictum dictum tincidunt, metus risus eleifend elit, a luctus sapien diam ut enim. Cras condimentum, velit sed convallis tincidunt, magna nulla porttitor quam, vel cursus nunc sem sit amet neque. Nullam pulvinar dolor lacinia enim mollis, vitae mollis justo pellentesque. Quisque vel metus a tortor congue aliquet sit amet non mauris.\n" +
                "\n" +
                "Suspendisse eu faucibus sapien, at pulvinar mi. Duis diam diam, faucibus sed massa et, ullamcorper facilisis lectus. Duis ut nulla vel odio imperdiet consectetur. Mauris et ante congue, varius justo eget, maximus felis. Curabitur eu eros ante. Sed tempus metus in erat sagittis, a luctus nulla imperdiet. Fusce nibh neque, efficitur nec sem quis, placerat pretium lorem. Fusce condimentum gravida ipsum, vel mattis ante luctus id. Cras tincidunt enim sit amet ligula luctus pretium.\n" +
                "\n" +
                "In placerat, magna sed tempus tempus, nisi dui luctus nisi, ac porta tellus quam eget magna. Nullam vitae tellus dui. Fusce egestas finibus purus ut placerat. Sed tincidunt pretium luctus. Nam consequat eu felis eu ornare. Duis efficitur auctor quam ac egestas. Fusce pellentesque ex magna, a posuere mauris sollicitudin vel. In condimentum nulla ante. " +
                "se enviarán").setPositiveButton("Aceptar", null).show();
    }
}
