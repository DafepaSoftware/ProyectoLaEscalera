package com.software.dafepa.proyectolaescalera;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

public class AboutActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private LinearLayout pablotwitter;
    private LinearLayout pabloGmail;
    private LinearLayout pabloFacebook;
    private LinearLayout pabloLinkedin;
    private LinearLayout ferGmail;
    private LinearLayout daniLinkedin;
    private LinearLayout daniGmail;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbarCode();
        activity = this;

        pabloLinkedin =(LinearLayout) findViewById(R.id.ly_linkedin_pablo);
        pabloGmail = (LinearLayout) findViewById(R.id.ly_correo_pablo);
        pablotwitter = (LinearLayout) findViewById(R.id.ly_twitter_pablo);
        pabloFacebook = (LinearLayout) findViewById(R.id.ly_facebook_pablo);
        ferGmail = (LinearLayout) findViewById(R.id.ly_gmail_fer);
        daniGmail = (LinearLayout) findViewById(R.id.ly_gmail_dani);
        daniLinkedin = (LinearLayout) findViewById(R.id.ly_linkedin_dani);

        daniLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLinkedin("daniel-menendez-mira-2099a9b5");
            }
        });

        daniGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGmail("daniel.menendez@hotmail.com");
            }
        });

        ferGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGmail("ferperezltb@gmai.com");
            }
        });

        pabloLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLinkedin("pablo-lira-0858a97");
            }
        });


        pablotwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTwitter("708108468624949248", "p4uman");
            }
        });

        pabloGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGmail("pliras95@gmail.com");
            }
        });

        pabloFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFacebook("100001079580249", "pau.lira.33");
            }
        });
    }

    public Intent startFacebook(String id, String username) {
        Intent intent = null;

        try {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/"+id));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/"+username)));
        }
        try{
            this.startActivity(intent);
        }catch (Exception e){

        }
        return intent;
    }

    public Intent startTwitter(String id, String username) {

        Intent intent = null;
        try {

            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=" + id));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + username));
        }
        this.startActivity(intent);
        return intent;
    }

    public Intent startGmail(String mail){
        Intent intent = null;
        try{
            intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + mail));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Halp.me");
            intent.putExtra(Intent.EXTRA_TEXT, "Hola!");
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            //TODO smth
        }
        this.startActivity(intent);
        return intent;
    }

    public void startLinkedin(String id){

        String pageId = id;
        final String urlFb = "linkedin://" + pageId;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));



        final PackageManager packageManager = this.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        if (list.size() == 0) {
            final String urlBrowser = "http://www.linkedin.com/in/" + pageId;
            intent.setData(Uri.parse(urlBrowser));
        }
        this.startActivity(intent);

    }





    private void toolbarCode(){
        myToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        myToolbar.setElevation(0.0f);
        myToolbar.setTitle("Sobre nosotros");
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        return super.onOptionsItemSelected(item);
    }

}
