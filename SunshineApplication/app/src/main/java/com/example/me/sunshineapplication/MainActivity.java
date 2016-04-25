package com.example.me.sunshineapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Log.d("Orientation Change","In on create");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Orientation Change", "In on start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Orientation Change", "In on resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Orientation Change", "In on pause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Orientation Change", "In on stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Orientation Change", "In on destroy");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

                Intent i=new Intent(this,SettingsActivity.class);
                startActivity(i);

            return true;
        }
        else
            return false;


    }
}
