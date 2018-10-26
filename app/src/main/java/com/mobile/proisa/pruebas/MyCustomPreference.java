package com.mobile.proisa.pruebas;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MyCustomPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener{
    private static final int MAX = 100;
    private TextView textView;
    private int value;

    public MyCustomPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setLayoutResource(R.layout.custom_seekbar_preference);
        setPositiveButtonText(R.string.ok);
        setNegativeButtonText(R.string.cancel);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        SeekBar bar = view.findViewById(R.id.seekBar);
        bar.setOnSeekBarChangeListener(this);
        bar.setMax(MAX);

        textView = view.findViewById(R.id.textView);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){
            value = progress;
        }

        textView.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, MAX);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if(positiveResult){
            persistInt(value);
        }
    }
}
