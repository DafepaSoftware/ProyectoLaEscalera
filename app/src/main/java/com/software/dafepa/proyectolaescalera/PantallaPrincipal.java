package com.software.dafepa.proyectolaescalera;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PantallaPrincipal extends AppCompatActivity {

    private Toolbar myToolbar;
    private DrawerLayout myDrawer;
    private Button btn_editUser;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        activity=this;

        btn_editUser = (Button) findViewById(R.id.btn_editarUsuario);


        btn_editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditUser.class);
                startActivity(intent);
            }
        });



        toolbarCode();
        navDrawerCode();
    }


    //Función para cargar la toolbar personalizada
    private void toolbarCode(){
        myToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        myToolbar.setElevation(0.0f);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    //Función para crear el navigation drawer
    private void navDrawerCode() {
        myDrawer = (DrawerLayout) findViewById(R.id.drawer_layout_mm_a);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, myDrawer, myToolbar, R.string.Abrir, R.string.Cerrar) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }


        }; // Drawer Toggle Object Made
        myDrawer.addDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

    }

    //Función necesaria para desplegar el navigation drawer
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }



}
