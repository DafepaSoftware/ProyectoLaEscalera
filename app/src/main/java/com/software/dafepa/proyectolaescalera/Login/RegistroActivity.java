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

import com.software.dafepa.proyectolaescalera.HalpFuncs;
import com.software.dafepa.proyectolaescalera.PantallaPrincipal;
import com.software.dafepa.proyectolaescalera.R;

public class RegistroActivity extends AppCompatActivity {

    //interfaz
    private Button btn_registrarse;
    private EditText usuario;
    private EditText mail;
    private EditText contrasena1;
    private EditText contrasena2;

    //Copia de activity para su fácil uso
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        activity = this;
        HalpFuncs.translucentStatusBar(activity);

        btn_registrarse =  (Button) findViewById(R.id.btn_registrame);
        contrasena1 = (EditText) findViewById(R.id.et_contrasena1);

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

        //TODO hacer funcionalidad
        /*btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //comprobar que hay datos en contraseña
                if(contrasena1.length()!=contrasena2.length()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Las contraseñas deben coincidir", Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Registro realizado con exito!", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent e = new Intent(RegistroActivity.this, PantallaPrincipal.class);
                    startActivity(e);
                    finish();
                }

            }
        });*/

    }

    //Botón atrás de Android
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
