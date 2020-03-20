package com.prime.calculator;

import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends NavigationViewActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toggleNavDrawerToolbar(false, true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.setting_activity, new SettingsFragment())
                .commit();
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        super.mToolbar.findViewById(R.id.toggle_mode).setVisibility(View.GONE);
        super.mToolbar.findViewById(R.id.btn_history).setVisibility(View.GONE);
    }
}
