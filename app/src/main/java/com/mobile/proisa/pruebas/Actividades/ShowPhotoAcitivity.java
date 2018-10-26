package com.mobile.proisa.pruebas.Actividades;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mobile.proisa.pruebas.R;

import java.io.File;

public class ShowPhotoAcitivity extends AppCompatActivity {
    public static final String TEST_FILENAME = "4580.jpg";
    public static final String DIRECTORIO_FOTOS_ARTICULOS = Environment.getExternalStorageDirectory().toString()+"/.Proisa/Articulos/";
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo_acitivity);

        final ImageView imageView = findViewById(R.id.imageView);

        //Environment.getExternalStorageDirectory(), "pp.jpeg"
        uri = Uri.fromFile(new File(DIRECTORIO_FOTOS_ARTICULOS, TEST_FILENAME));
        imageView.setImageURI(uri);



        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener());
        imageView.setOnTouchListener(new ZoomListener(gestureDetector));


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_back_dark);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(intent, getString(R.string.share_msg)));
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }
}
