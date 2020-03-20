package com.prime.calculator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.prime.calculator.Utility.PrefManager;
import com.prime.calculator.Utility.Utils;

/**
 * This Class extends from @Link : ThemeActivity and implements Toolbar And Navigation View to every
 * Activity
 * It also Initialize Admob Banner & Interetial Ads
 *
 * @author Zakir Ahmad Sheikh
 * @since 26-03-2019
 */
public class NavigationViewActivity extends ThemeActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    protected Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private Handler mHandler = new Handler();
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (showAds())
            mHandler.postDelayed(() -> setupAdmob(true, true), 250);
        setupToolbar();
        PrefManager prefManager = PrefManager.getInstance(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !prefManager
                .getBooleanPref("Theme_Switch", false))
            setLightStatusBar(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setLightStatusBar(Activity activity) {
        int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
        activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        activity.getWindow().setStatusBarColor(Color.WHITE); // optional
    }

    /**
     * toggles Ads in App
     *
     * @return true if yes otherwise False
     */
    protected boolean showAds() {
        return true;
    }

    /**
     * is InterestitialAdLoaded?
     *
     * @return true if yes otherwise False
     */
    protected boolean isInterstitialAdLoaded() {
        return mInterstitialAd.isLoaded();
    }

    /**
     * This Method Sets up Admob ads
     *
     * @param showBannerAd       true if yes otherwise false
     * @param showInterstitialAd true if yes otherwise false
     */
    protected void setupAdmob(boolean showBannerAd, boolean showInterstitialAd) {
        AdView mAdView;
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("9CAB83BE7A93CD68E713ABEB8BEC8A7D")
                .build();
        if (showBannerAd) {
            mAdView = findViewById(R.id.adView);
            mAdView.setVisibility(View.VISIBLE);
            mAdView.loadAd(adRequest);
        }
        if (showInterstitialAd) {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getString(R.string.interstitial_AdUnitId));
            mInterstitialAd.loadAd(adRequest);
        }
    }

    /**
     * This Method Sets up Navigation View
     */
    protected void setupNavView() {
        NavigationView mNavView = findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(this);
        mNavView.setCheckedItem(getSelectedNavItem());

    }

    /**
     * This method toggles the visibility of navigation View and Toolbar.
     * if Navigation view is disabled Toolbar Must also be disabled as far as I Like
     * Navigation view is only used for Activities.
     * Toolbar may be used for both
     * if navigation View is disabled, back arrow of Toolbar must be enabled and hence DrawerToggle
     * must be disabled
     *
     * @param enableNavView : provide true if yes otherwise false
     * @param enableToolbar : provide true if yes otherwise false
     */
    protected void toggleNavDrawerToolbar(boolean enableNavView, boolean enableToolbar) {
        if (enableNavView && enableToolbar) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                    R.string.nav_drawer_opened,
                    R.string.nav_drawer_closed) {
                @Override
                public void onDrawerStateChanged(int newState) {
                    super.onDrawerStateChanged(newState);
                    hideKeyboard();
                }
            };
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mDrawerLayout.addDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();
        } else if (!enableNavView && enableToolbar) {
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else if (!enableNavView && !enableToolbar) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mToolbar.setVisibility(View.GONE);
        }
    }

    /**
     * This method sets up Toolbar
     */
    protected void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    /**
     * This Function hides IMM Whenever Called
     */
    protected void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * This Function Sets Incoming layoutResID to Frame Layout
     *
     * @param layoutResID : Incoming layoutResID
     */
    @Override

    public void setContentView(int layoutResID) {
        mDrawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_navigation_view, null);
        FrameLayout activityContainer = mDrawerLayout.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(mDrawerLayout);
        setupToolbar();
        setupNavView();
        toggleNavDrawerToolbar(true, true);
    }

    /**
     * This Function Selects the Item of Navigation View corresponding to Activity Which is currently
     * Visible
     *
     * @return : ID of Activity
     */
    protected int getSelectedNavItem() {
        return R.id.nav_standard;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START, false);
        if (item.getItemId() == getSelectedNavItem())
            return false;
        return onOptionsItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.nav_standard:
                startActivity(new Intent(this, StandardActivity.class));
                if (isInterstitialAdLoaded())
                    mInterstitialAd.show();
                finish();
                break;
            case R.id.nav_scientific:
                startActivity(new Intent(this, ScientificActivity.class));
                if (isInterstitialAdLoaded())
                    mInterstitialAd.show();
                finish();
                break;
            case R.id.nav_programmer:
                Utils.showToast(this, "This Will be added Soon!");
                break;
            case R.id.nav_date_calculatione:
                startActivity(new Intent(this, DateCalculationActivity.class));
                if (isInterstitialAdLoaded())
                    mInterstitialAd.show();
                finish();
                break;
            case R.id.nav_converter:
                startActivity(new Intent(this, UnitConverterActivity.class));
                if (isInterstitialAdLoaded())
                    mInterstitialAd.show();
                finish();
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_about_us:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else if (!isTaskRoot())
            super.onBackPressed();
        else {
            final Dialog dialog = new Dialog(this);
            // Include dialog.xml file
            dialog.setContentView(R.layout.custom_alert_dialogue);
            dialog.show();
            Button positiveButton = dialog.findViewById(R.id.positive_button);
            Button negativeButton = dialog.findViewById(R.id.negative_button);
            positiveButton.setOnClickListener(v -> finishAndRemoveTask());
            negativeButton.setOnClickListener(v -> dialog.dismiss());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}

