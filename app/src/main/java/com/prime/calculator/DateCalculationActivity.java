package com.prime.calculator;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class DateCalculationActivity extends NavigationViewActivity {

    TabLayout tabLayout;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_calculation);
        ViewPager viewPager = findViewById(R.id.date_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DateDifferenceFragment(), "Date Difference");
        adapter.addFragment(new AddSubtractDatesFragment(), "Add/Subtract Dates");
        viewPager.setAdapter(adapter);
        tabLayout = findViewById(R.id.date_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        super.mToolbar.findViewById(R.id.toggle_mode).setVisibility(View.GONE);
        super.mToolbar.findViewById(R.id.btn_history).setVisibility(View.GONE);
        super.mToolbar.setElevation(0);
    }

    @Override
    protected int getSelectedNavItem() {
        return R.id.nav_date_calculatione;
    }

    @Override
    protected void hideKeyboard() {
        super.hideKeyboard();
    }

    /**
     * This Class Sets up View Pager Adapter
     */
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
