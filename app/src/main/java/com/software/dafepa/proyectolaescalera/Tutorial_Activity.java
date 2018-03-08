package com.software.dafepa.proyectolaescalera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.robertlevonyan.views.expandable.Expandable;
import com.robertlevonyan.views.expandable.ExpandingListener;

public class Tutorial_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_);

        final Expandable expandable = findViewById(R.id.expandable);

        expandable.setExpandingListener(new ExpandingListener() {
            @Override
            public void onExpanded() {
                expandable.changeBackgroundColor(R.color.moradete); // Set a custom background color for layout

            }

            @Override
            public void onCollapsed() {
                //some stuff on collapse
            }
        });


    }
}
