package com.software.dafepa.proyectolaescalera;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.software.dafepa.proyectolaescalera.Adapters.Adapter_NavDrawer;
import com.software.dafepa.proyectolaescalera.Adapters.Adapter_eventos;
import com.software.dafepa.proyectolaescalera.Login.RegistroActivity;
import com.software.dafepa.proyectolaescalera.Login.SplashActivity;
import com.software.dafepa.proyectolaescalera.Objects.Evento;
import com.software.dafepa.proyectolaescalera.Objects.Usuario;
import com.software.dafepa.proyectolaescalera.Singletones.AplicacionManager;
import com.software.dafepa.proyectolaescalera.Utilidades.ApplicationData;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.zip.Inflater;

public class PantallaPrincipal extends AppCompatActivity {

    private Toolbar myToolbar;
    private DrawerLayout myDrawer;
    private static Activity activity;
    private static int tabstart = 0;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ListView lv_navdrawer;
    private Button btn_caballo;
    private CircularImageView img_user;

    /*private ProgressBar progressBar;
    private RelativeLayout ly_main;*/


    private static Adapter_eventos busco_adapter;
    private static Adapter_eventos ofrezco_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        btn_caballo = (Button) findViewById(R.id.btn_pene);
        activity=this;

        busco_adapter = new Adapter_eventos(activity);
        ofrezco_adapter = new Adapter_eventos(activity);

        btn_caballo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(activity,InfoElemeneto.class);
                startActivity(inte);
            }
        });


        Usuario usu = AplicacionManager.getInstance().getUsuario();
        TextView txt_nombre = findViewById(R.id.txt_nombre);
        txt_nombre.setText(usu.getNick());
        TextView txt_correo = findViewById(R.id.txt_correo);
        txt_correo.setText(usu.getMail());
        //Botón flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, NuevoEvento.class);
                startActivity(intent);
            }
        });

        img_user = findViewById(R.id.img_user);
        img_user.setImageDrawable(new BitmapDrawable(getResources(), usu.getImg()));

        toolbarCode();
        navDrawerCode();
        initViewPager();

        //cargarEventos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadItems();

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

        title.add("Nuevo Halp");


        title.add("Ayuda");
        title.add("Sobre nosotros");
        title.add("Desconectar");




        img_id.add(R.drawable.verdehalpp);
        img_id.add(R.drawable.information);
        img_id.add(R.drawable.quienes_somos);
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
                finish();
            }
        });

        lv_navdrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:{
                        Intent intent = new Intent(activity, NuevoEvento.class);
                        startActivity(intent);
                        break;
                    }
                    case 1:{
                        Intent intent = new Intent(activity,Tutorial_Activity.class);
                        startActivity(intent);
                        break;
                    }
                    case 2:{
                        Intent intent = new Intent(activity, AboutActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 3:{
                        Intent intent = new Intent(activity, SplashActivity.class);
                        startActivity(intent);
                        ApplicationData appdata = new ApplicationData();
                        appdata.cargarAplicacionDePreferencias(activity);
                        appdata.setUsuario_nick("");
                        appdata.setRememberme(false);
                        appdata.guardarEnPreferencias(activity);
                        finish();
                        break;
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
                    ListView lv = rootView.findViewById(R.id.fragment_lv);
                    lv.setAdapter(busco_adapter);

                    final SwipeRefreshLayout ly = rootView.findViewById(R.id.ly_refresh);
                    ly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            reloadItems();
                            ly.setRefreshing(false);
                        }
                    });

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(activity, InfoElemeneto.class);
                            startActivity(intent);
                            AplicacionManager.getInstance().setEvento(busco_adapter.getEventos().get(i));
                        }
                    });
                    break;
                }
                case 2:{
                    rootView = inflater.inflate(R.layout.fragment_busco, container, false);
                    ListView lv = rootView.findViewById(R.id.fragment_lv);
                    lv.setAdapter(ofrezco_adapter);

                    final SwipeRefreshLayout ly = rootView.findViewById(R.id.ly_refresh);
                    ly.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            reloadItems();
                            ly.setRefreshing(false);
                        }
                    });

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(activity, InfoElemeneto.class);
                            startActivity(intent);
                            AplicacionManager.getInstance().setEvento(ofrezco_adapter.getEventos().get(i));
                        }
                    });

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


    private static void cargarEventos(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference event_ref = database.getReference("halpme/eventos");


        event_ref.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                final Evento e = dataSnapshot.getValue(Evento.class);

                final FirebaseStorage storage;
                StorageTask<FileDownloadTask.TaskSnapshot> storageReference;
                storage = FirebaseStorage.getInstance();
                final File localFile;

                try {
                    Random r = new Random();
                    String path = "tmp" +r.nextInt(999999) + "_" + e.getID();
                    localFile = File.createTempFile(path, "jpg");
                    storage.getReference("images/eventos/" + e.getID()).
                            getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            e.setImg(bitmap);
                            Random r = new Random();
                            String path = "tmp" +r.nextInt(999999) + "_" + e.getNick_usuario();
                            final File localFile2;
                            try {
                                localFile2 = File.createTempFile(path, "jpg");
                                storage.getReference("images/usuarios/" + e.getNick_usuario()).
                                        getFile(localFile2).addOnSuccessListener(
                                                new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap = BitmapFactory.decodeFile(localFile2.getAbsolutePath());
                                        e.setImg_usuario(bitmap);
                                        if(e.getBusco()){
                                            busco_adapter.getEventos().add(0,e);
                                            busco_adapter.notifyDataSetChanged();

                                        }else{
                                            ofrezco_adapter.getEventos().add(0,e);
                                            ofrezco_adapter.notifyDataSetChanged();
                                        }
                                    }
                                });

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                        }
                    });


                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            @Override
            protected void finalize() throws Throwable {

            }
        });
    }

    private static void reloadItems(){
        busco_adapter.getEventos().clear();
        ofrezco_adapter.getEventos().clear();
        cargarEventos();

    }

}
