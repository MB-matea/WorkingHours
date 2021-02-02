package com.mateabeslic.workinghours;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.service.quickaccesswallet.QuickAccessWalletService;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

public class EmployeeDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_EMPLOYEEID = "employeeID";
    public static String idString;

//    EditText date, timeStart, timeEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final int employeeId = getIntent().getExtras().getInt(EXTRA_EMPLOYEEID);
        idString = String.valueOf(employeeId);


        //Poveži adapter sa ViewPager-om
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ViewPager vp = findViewById(R.id.pager);
        vp.setAdapter(homePagerAdapter);

        //Poveži viewpager sa tablayoutom
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vp);
   }



    private class HomePagerAdapter extends FragmentPagerAdapter {

        public HomePagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return sendDataAddTimeFragment();
                case 1:
                    return sendDataGetTimeFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.addTime_tab);
                case 1:
                    return getResources().getText(R.string.getTime_tab);
                case 2:
            }

            return null;
        }
    }

    private AddTimeFragment sendDataAddTimeFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("id", Integer.valueOf(idString));
        AddTimeFragment fragment = new AddTimeFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private GetTimeFragment sendDataGetTimeFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("id", Integer.valueOf(idString));
        GetTimeFragment fragment = new GetTimeFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

}