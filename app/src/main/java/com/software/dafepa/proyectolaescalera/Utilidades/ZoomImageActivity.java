package com.software.dafepa.proyectolaescalera.Utilidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.software.dafepa.proyectolaescalera.R;

/**
 * Created by pablolira on 1/3/18.
 */

public class ZoomImageActivity extends AppCompatActivity {

        private Toolbar myToolbar;

        private SubsamplingScaleImageView imgImagen;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.zoom_imagen);

            toolbarCode();
            imgImagen = (SubsamplingScaleImageView)findViewById(R.id.imgImagen);
            imgImagen.setMaxScale(900);

            Bundle ex = getIntent().getExtras();
            byte[] bmp2 = ex.getByteArray("BitmapImage");

            Bitmap bitmap = BitmapFactory.decodeByteArray(bmp2, 0, bmp2.length);

            imgImagen.setImage(ImageSource.bitmap(bitmap));


        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }

    private void toolbarCode(){
        myToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        myToolbar.setElevation(0.0f);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

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
        return true;
    }
}

