package com.mobile.proisa.pruebas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.proisa.pruebas.Actividades.ShowPhotoAcitivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button b2, b1;

    public static final String TEST_FILENAME = "L4580.jpg";
    public static final String DIRECTORIO_FOTOS_ARTICULOS = Environment.getExternalStorageDirectory().toString()+"/.Proisa/Articulos/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.configuraciones_generales,false);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        b2 = findViewById(R.id.b2);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PreferencesActivity.class));
            }
        });

        b1 = findViewById(R.id.b1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printSqlConnectionString(getSqlData());
            }
        });



        startActivity(new Intent(this, ShowPhotoAcitivity.class));

    }

    public void printSqlConnectionString(SqlConnection.DbData dbData){
        TextView textView = findViewById(R.id.txt_config);

        SqlConnection connection = new SqlConnection(dbData);


        textView.setText(connection.getConnectionString());


    }

    public SqlConnection.DbData getSqlData(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SqlConnection.DbData data = new SqlConnection.DbData();

        data.setServer(preferences.getString(PreferencesActivity.PREF_SERVER, ""));
        data.setDatabase(preferences.getString(PreferencesActivity.PREF_BDNAME, ""));
        data.setPassword(preferences.getString(PreferencesActivity.PREF_PASSWORD, ""));
        data.setUser(preferences.getString(PreferencesActivity.PREF_USER, ""));
        data.setPort(Integer.parseInt(preferences.getString(PreferencesActivity.PREF_PORT,"0")));


        int days = Integer.parseInt(preferences.getString(PreferencesActivity.PREF_DAYS,
                "0"));
        Toast.makeText(getApplicationContext(),String.format("Cotizaciones desde hoy a %d dias\n",days) , Toast.LENGTH_SHORT).show();
        return data;
    }
}
