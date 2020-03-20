package com.prime.calculator;

import android.os.Bundle;

import com.prime.calculator.Utility.PrefManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

/* This Activity Will Implement Theme Changes Dynamically*/

public class ThemeActivity extends AppCompatActivity {
    private Boolean mSwitch;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        prefManager = PrefManager.getInstance(this);
        mSwitch = prefManager.getBooleanPref(PrefManager.THEME_SWITCH, false);
        if (mSwitch)
            setAppTheme(PrefManager.DARK_THEME);
        else
            setAppTheme(PrefManager.LIGHT_THEME);

    }

    private void setAppTheme(int setTheme) {
        if (setTheme == PrefManager.DARK_THEME)
            setTheme(R.style.Dark);
        else
            setTheme(R.style.Light);
    }

    //This Method is only for other activities that r below the stack;
    @Override
    protected void onResume() {
        super.onResume();
        Boolean selectedTheme = prefManager.getBooleanPref(PrefManager.THEME_SWITCH, false);
        if (selectedTheme != mSwitch)
            recreate();
    }
}