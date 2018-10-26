package com.mobile.proisa.pruebas;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.io.File;

public class GlideTestActivity extends AppCompatActivity {

    public static final String TEST_FILENAME = "L4580.jpg";
    public static final String DIRECTORIO_FOTOS_ARTICULOS = Environment.getExternalStorageDirectory().toString()+"/.Proisa/Articulos/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_test);

        ImageView imageView = findViewById(R.id.imageView);

        Uri uri = Uri.fromFile(new File( DIRECTORIO_FOTOS_ARTICULOS, TEST_FILENAME));

        /*Glide.with(this)
                .load(uri)
                .into(imageView).onLoadFailed(getResources().getDrawable(R.drawable.ic_account_user));*/


        Glide.with(getApplicationContext()).load(R.drawable.ic_account_user).into(imageView);
    }
}
