package dafepa.software.com.proyectolaescalera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainScreen extends AppCompatActivity {

    int a = 100;
    String b = "Contigo Pipo";
    int c = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        a+=20;
        System.out.println("Esto es una prueba" + a + b + 20);
    }
}
