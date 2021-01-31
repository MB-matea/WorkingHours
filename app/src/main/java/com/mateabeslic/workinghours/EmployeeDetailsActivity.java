package com.mateabeslic.workinghours;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

public class EmployeeDetailsActivity extends AppCompatActivity {

//    EditText date, timeStart, timeEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Poveži adapter sa ViewPager-om
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ViewPager vp = findViewById(R.id.pager);
        vp.setAdapter(homePagerAdapter);

        //Poveži viewpager sa tablayoutom
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vp);

//        date = findViewById(R.id.date);
//        timeStart = findViewById(R.id.time_start);
//        timeEnd = findViewById(R.id.time_end);
//
//
//        date.setInputType(InputType.TYPE_NULL);
//        timeStart.setInputType(InputType.TYPE_NULL);
//        timeEnd.setInputType(InputType.TYPE_NULL);
//
//
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDateDialog(date);
//            }
//        });
//
//        timeStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTimeDialog(timeStart);
//            }
//        });
//
//        timeEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTimeDialog(timeEnd);
//            }
//        });
//
//
//    private void showTimeDialog(EditText timeStart) {
//        Calendar calendar = Calendar.getInstance();
//        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                calendar.set(Calendar.MINUTE, minute);
//
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
//
//                timeStart.setText(simpleDateFormat.format(calendar.getTime()));
//            }
//        };
//        new TimePickerDialog(EmployeeDetailsActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
//                calendar.get(Calendar.MINUTE), true).show();
//    }
//
//    private void showDateDialog(EditText dateStart) {
//        final Calendar calendar = Calendar.getInstance();
//        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                calendar.set(Calendar.YEAR, year);
//                calendar.set(Calendar.MONTH, month);
//                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
//
//                dateStart.setText(simpleDateFormat.format(calendar.getTime()));
//            }
//        };
//        new DatePickerDialog(EmployeeDetailsActivity.this,dateSetListener, calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
   }

    private class HomePagerAdapter extends FragmentPagerAdapter {

        public HomePagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AddTimeFragment();
                case 1:
                    return new GetTimeFragment();
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
}