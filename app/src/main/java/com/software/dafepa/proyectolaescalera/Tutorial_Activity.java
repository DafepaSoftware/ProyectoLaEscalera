package com.software.dafepa.proyectolaescalera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.robertlevonyan.views.expandable.Expandable;
import com.robertlevonyan.views.expandable.ExpandingListener;

public class Tutorial_Activity extends AppCompatActivity {

    private Toolbar myToolbar;

    private Expandable espantable_que_asusta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_);

        espantable_que_asusta = findViewById(R.id.expandable);

        //hacer cosas de esas pa accedeer al text del layout de dentro


        toolbarCode();
        espantable_que_asusta.setExpandingListener(new ExpandingListener() {


            @Override
            public void onExpanded() {
                espantable_que_asusta.changeBackgroundColor(getColor(R.color.moradete)); // Set a custom background color for layout

            }

            @Override
            public void onCollapsed() {
                espantable_que_asusta.changeBackgroundColor(getColor(R.color.moradete)); // Set a custom background color for layout
            }
        });


    }

    private void toolbarCode() {
        myToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        myToolbar.setElevation(0.0f);
        myToolbar.setTitle(getResources().getString(R.string.preguntasfrecuentes));
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);    }

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
        }
        return super.onOptionsItemSelected(item);
    }
}


