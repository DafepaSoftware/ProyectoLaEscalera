package com.software.dafepa.proyectolaescalera.Login;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import java.util.Map;

//TODO deberíamos limitar los campos de alguna manera y hacerla scrollable
public class RegistroActivity extends AppCompatActivity {

    //interfaz
    private Button btn_registrarse;
    private EditText edtxt_nombre;
    private EditText edtxt_apellido;
    private EditText edtxt_correo;
    private EditText edtxt_pass;
    private EditText edtxt_pass2;


    //Copia de activity para su fácil uso
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        activity = this;
        HalpFuncs.translucentStatusBar(activity);

        edtxt_nombre = findViewById(R.id.edtxt_nombre);
        edtxt_apellido = findViewById(R.id.edtxt_apellido);
        edtxt_correo = findViewById(R.id.edtxt_correo);
        edtxt_pass = (EditText) findViewById(R.id.edtxt_pass);
        edtxt_pass2 = (EditText) findViewById(R.id.edtxt_pass2);
        btn_registrarse =  (Button) findViewById(R.id.btn_registrame);

        (findViewById(R.id.btn_terminos)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity).setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis ligula pharetra odio varius posuere sit amet non ligula. Donec eu efficitur nisi. Ut risus massa, tincidunt vel urna eget, gravida venenatis justo. Ut pulvinar porttitor mi non luctus. Nullam rutrum odio at purus fermentum dictum. In sagittis metus lectus, id volutpat quam venenatis at. Phasellus id tristique urna, sed fermentum felis. Suspendisse luctus, sapien nec volutpat condimentum, eros libero ultrices ex, nec porta urna lacus a sapien. Integer rutrum eu neque tincidunt pulvinar. Quisque consectetur, ex dictum dictum tincidunt, metus risus eleifend elit, a luctus sapien diam ut enim. Cras condimentum, velit sed convallis tincidunt, magna nulla porttitor quam, vel cursus nunc sem sit amet neque. Nullam pulvinar dolor lacinia enim mollis, vitae mollis justo pellentesque. Quisque vel metus a tortor congue aliquet sit amet non mauris.\n" +
                        "\n" +
                        "Suspendisse eu faucibus sapien, at pulvinar mi. Duis diam diam, faucibus sed massa et, ullamcorper facilisis lectus. Duis ut nulla vel odio imperdiet consectetur. Mauris et ante congue, varius justo eget, maximus felis. Curabitur eu eros ante. Sed tempus metus in erat sagittis, a luctus nulla imperdiet. Fusce nibh neque, efficitur nec sem quis, placerat pretium lorem. Fusce condimentum gravida ipsum, vel mattis ante luctus id. Cras tincidunt enim sit amet ligula luctus pretium.\n" +
                        "\n" +
                        "In placerat, magna sed tempus tempus, nisi dui luctus nisi, ac porta tellus quam eget magna. Nullam vitae tellus dui. Fusce egestas finibus purus ut placerat. Sed tincidunt pretium luctus. Nam consequat eu felis eu ornare. Duis efficitur auctor quam ac egestas. Fusce pellentesque ex magna, a posuere mauris sollicitudin vel. In condimentum nulla ante. " +
                        "se enviarán").setPositiveButton("Aceptar", null).show();
            }
        });

        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobacionesCampos();

            }
        });

    }

    //Botón atrás de Android
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void comprobacionesCampos(){
        if (edtxt_nombre.getText().toString().length() <= 0){
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
            finish();
        }
    }

    private void crearUsuario(){
        Usuario u = new Usuario();
        u.setNombre(edtxt_nombre.getText().toString());
        u.setApellido(edtxt_apellido.getText().toString());
        u.setContrasena(edtxt_pass.getText().toString());
        u.setMail(edtxt_correo.getText().toString());

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("halp.me");
        DatabaseReference usersRef = ref.child("usuarios");


        usersRef.setValue(u);



    }
}
