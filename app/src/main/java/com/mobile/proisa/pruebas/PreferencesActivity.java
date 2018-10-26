package com.mobile.proisa.pruebas;


import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

public class PreferencesActivity extends AppCompatActivity {
    public static final String PREF_SERVER = "server_pref";
    public static final String PREF_PORT = "port_pref";
    public static final String PREF_PASSWORD = "pass_pref";
    public static final String PREF_BDNAME = "bdname_pref";
    public static final String PREF_USER = "user_pref";
    public static final String PREF_DAYS =  "days_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        setTitle(getString(R.string.settings_str));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setFragment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setFragment(){
        MyPreferenceFragment fragment = new MyPreferenceFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

}
