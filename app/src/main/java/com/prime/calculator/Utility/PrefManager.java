package com.prime.calculator.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class PrefManager {

    public static final String PREF_CONVERTER = "Converter";
    public static final String FROM_UNIT_ID = "From_Unit";
    public static final String TO_UNIT_ID = "To_Unit";
    public static final String FROM_UNIT_LABEL = "From_Unit_Label";
    public static final String TO_UNIT_LABEL = "To_Unit_Label";
    public static final String UNT_CTR_FIRST_RUN = "First_Unt_Ctr_Run";
    public static final String CURRENCY_FROM_LABEL = "Currency_From_Label";
    public static final String CURRENCY_TO_LABEL = "Currency_To_Label";
    public static final String CURRENCY_FROM_ID = "Currency_From_Id";
    public static final String CURRENCY_TO_ID = "Currency_To_Id";
    public static final String CURRENCY_UPDATE_DATE = "Currency_Update_date";
    public static final String CURRENCY_KEY_COUNT = "Currency_Key_Count";
    public static final String CURRENCY_API_KEY = "Currency_API_Key";

    //Theme Preferences
    public static final String THEME_SWITCH = "Theme_Switch";
    public static final int LIGHT_THEME = 0;
    public static final int DARK_THEME = 1;






    private static PrefManager mInstance = null;
    private SharedPreferences sharedPref;

    private PrefManager(Context context) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PrefManager(context.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Get instance of app's Shared PrefManager
     *
     * @return SharedPreference instance
     */
    public SharedPreferences getPreferences() {
        return sharedPref;
    }

    public void setIntegerPref(String prefName, int value) {
        sharedPref.edit().putInt(prefName, value).apply();
    }

    public int getIntegerPref(String prefName, int defaultValue) {
        return sharedPref.getInt(prefName, defaultValue);
    }

    public void setStringPref(String prefName, String value){
        sharedPref.edit().putString(prefName, value).apply();
    }

    public String getStringPref(String prefName, String defaultValue){
        return sharedPref.getString(prefName, defaultValue);
    }

    public void setBooleanPref(String prefName, boolean value){
        sharedPref.edit().putBoolean(prefName, value).apply();
    }

    public boolean getBooleanPref(String prefName, boolean defaultValue){
        return sharedPref.getBoolean(prefName, defaultValue);
    }
}
