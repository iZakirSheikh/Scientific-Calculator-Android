package com.prime.calculator;


import android.app.Fragment;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState,
                                    String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preference preference = this.findPreference("Theme_Switch");
        assert preference != null;
        preference.setOnPreferenceChangeListener((preference1, newValue) -> {
            getActivity().recreate();
            return true;
        });
    }

}