package com.software.dafepa.proyectolaescalera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.software.dafepa.proyectolaescalera.Adapters.Adapter_NavDrawer;
import com.software.dafepa.proyectolaescalera.Login.RegistroActivity;
import com.software.dafepa.proyectolaescalera.Login.SplashActivity;
import com.software.dafepa.proyectolaescalera.Objects.Usuario;
import com.software.dafepa.proyectolaescalera.Utilidades.ApplicationData;

import java.util.ArrayList;

public class PantallaPrincipal extends AppCompatActivity {

    private Toolbar myToolbar;
    private DrawerLayout myDrawer;
    private Activity activity;
    private static int tabstart = 0;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ListView lv_navdrawer;
    private Button btn_caballo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        btn_caballo = (Button) findViewById(R.id.btn_pene);
        activity=this;

        btn_caballo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(activity,InfoElemeneto.class);
                startActivity(inte);
            }
        });


        //Botón flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, NuevoEvento.class);
                startActivity(intent);
            }
        });


        toolbarCode();
        navDrawerCode();
        initViewPager();
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

        crearNavDrawer();
    }

    //Función para crear los elementos del navdrawer
    private void crearNavDrawer(){
        ArrayList<String> title = new ArrayList<>();
        ArrayList<Integer> img_id = new ArrayList<>();

        title.add("Nuevo evento");
        title.add("Mis eventos");


        title.add("Ayuda");
        title.add("Desconectar");




        img_id.add(R.drawable.verdehalpp);
        img_id.add(R.drawable.map);
        img_id.add(R.drawable.information);
        img_id.add(R.drawable.powerbutton);

        Adapter_NavDrawer adpter = new Adapter_NavDrawer(activity, title, img_id );
        lv_navdrawer = findViewById(R.id.lv_navdrawer);
        lv_navdrawer.setAdapter(adpter);

        LinearLayout ly_perfil = findViewById(R.id.ly_perfil);
        ly_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EditUser.class);
                startActivity(intent);
            }
        });

        lv_navdrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 3:{
                        Intent intent = new Intent(activity, SplashActivity.class);
                        startActivity(intent);
                        ApplicationData appdata = new ApplicationData();
                        appdata.cargarAplicacionDePreferencias(activity);
                        appdata.setUser(new Usuario());
                        appdata.setRememberme(false);
                        appdata.guardarEnPreferencias(activity);
                        finish();
                    }
                }
            }
        });
    }

    //Función necesaria para desplegar el navigation drawer
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }




    /** CÓDIGO PARA EL SWIPE**/


    private void initViewPager(){
        tabstart = getIntent().getIntExtra("tab", 0);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.color_fondo),
                getResources().getColor(R.color.blanco));

        mViewPager.setCurrentItem(tabstart);
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();

            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:{
                    rootView = inflater.inflate(R.layout.fragment_busco, container, false);


                    break;
                }
                case 2:{
                    rootView = inflater.inflate(R.layout.fragment_busco, container, false);

                    break;
                }
            }
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "BUSCO";
                case 1:
                    return "OFREZCO";
            }
            return null;
        }
    }


}
