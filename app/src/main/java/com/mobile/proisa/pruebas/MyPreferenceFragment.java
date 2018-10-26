package com.mobile.proisa.pruebas;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;

import android.preference.PreferenceDataStore;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    private static final int NOTIFICATION_ID = 300;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.configuraciones_generales);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        super.setPreferenceScreen(preferenceScreen);

        setSumarryForPreferences(preferenceScreen.getSharedPreferences());
    }

    private void setSumarryForPreferences(SharedPreferences sharedPreferences){
        setSummaryNoSpecial(PreferencesActivity.PREF_SERVER, sharedPreferences.getString(PreferencesActivity.PREF_SERVER,""));
        setSummaryNoSpecial(PreferencesActivity.PREF_BDNAME, sharedPreferences.getString(PreferencesActivity.PREF_BDNAME, ""));
        setSummaryNoSpecial(PreferencesActivity.PREF_USER, sharedPreferences.getString(PreferencesActivity.PREF_USER,""));

        /*Contraseña*/
        boolean isEmpty = TextUtils.isEmpty(sharedPreferences.getString(PreferencesActivity.PREF_PASSWORD,""));
        String setOrNOt = isEmpty ? getString(R.string.noSet) : getString(R.string.set);
        setSummaryNoSpecial(PreferencesActivity.PREF_PASSWORD, setOrNOt);
        /*Fin Contraseña*/

        setSummaryNoSpecial(PreferencesActivity.PREF_PORT, sharedPreferences.getString(PreferencesActivity.PREF_PORT,""));

        int convertDays = Integer.parseInt(sharedPreferences.getString(PreferencesActivity.PREF_DAYS, "0"));

        boolean isNoSelected = convertDays == 0;

        String strDays = isNoSelected ? getString(R.string.no_limit) :
                String.format(getString(R.string.string_format_days),convertDays);

        setSummaryNoSpecial(PreferencesActivity.PREF_DAYS, strDays);
    }

    private void setSummaryNoSpecial(String key, String value){
        Preference preference = findPreference(key);
        preference.setSummary(value);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        setSumarryForPreferences(sharedPreferences);

        sendNotification();
    }


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }


    /**
     * Send a sample notification using the NotificationCompat API.
     */
    public void sendNotification() {

        // BEGIN_INCLUDE(build_action)
        /** Create an intent that will be fired when the user clicks the notification.
         * The intent needs to be packaged into a {@link android.app.PendingIntent} so that the
         * notification service can fire it on our behalf.
         */
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);
        // END_INCLUDE(build_action)

        // BEGIN_INCLUDE (build_notification)
        /**
         * Use NotificationCompat.Builder to set up our notification.
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());

        /** Set the icon that will appear in the notification bar. This icon also appears
         * in the lower right hand corner of the notification itself.
         *
         * Important note: although you can use any drawable as the small icon, Android
         * design guidelines state that the icon should be simple and monochrome. Full-color
         * bitmaps or busy images don't render well on smaller screens and can end up
         * confusing the user.
         */
        builder.setSmallIcon(R.drawable.ic_stat_notification);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Set the notification to auto-cancel. This means that the notification will disappear
        // after the user taps it, rather than remaining until it's explicitly dismissed.
        builder.setAutoCancel(true);

        /**
         *Build the notification's appearance.
         * Set the large icon, which appears on the left of the notification. In this
         * sample we'll set the large icon to be the same as our app icon. The app icon is a
         * reasonable default if you don't have anything more compelling to use as an icon.
         */
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        /**
         * Set the text of the notification. This sample sets the three most commononly used
         * text areas:
         * 1. The content title, which appears in large type at the top of the notification
         * 2. The content text, which appears in smaller text below the title
         * 3. The subtext, which appears under the text on newer devices. Devices running
         *    versions of Android prior to 4.2 will ignore this field, so don't use it for
         *    anything vital!
         */
        builder.setContentTitle("Han cambiado la configuracion");
        builder.setContentText("Time to learn about notifications!");
        builder.setSubText("Tap to view documentation about notifications.");
        builder.setVibrate(new long[]{1000,1000,1000});

        // END_INCLUDE (build_notification)

        // BEGIN_INCLUDE(send_notification)
        /**
         * Send the notification. This will immediately display the notification icon in the
         * notification bar.
         */
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        // END_INCLUDE(send_notification)
    }
}
