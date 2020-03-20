package com.prime.calculator;


import android.os.Bundle;
import android.view.View;

public class AboutActivity extends NavigationViewActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toggleNavDrawerToolbar(false, true);
    }

    @Override
    protected boolean showAds() {
        return false;
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        super.mToolbar.findViewById(R.id.btn_history).setVisibility(View.GONE);
        super.mToolbar.findViewById(R.id.toggle_mode).setVisibility(View.GONE);
    }
}
