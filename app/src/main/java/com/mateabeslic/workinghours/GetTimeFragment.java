package com.mateabeslic.workinghours;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mateabeslic.workinghours.Database.EmployeeDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;


public class GetTimeFragment extends Fragment {

    private EmployeeDatabase employeeDatabase;
    private TextView txtHours;
    private String allHoursString;
    private View layout;

    public GetTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            allHoursString = savedInstanceState.getString("extra");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_get_time, container, false);

        Spinner spinner = layout.findViewById(R.id.spn_months);
        Button btnShow = layout.findViewById(R.id.btn_show);
        txtHours = layout.findViewById(R.id.txt_hours);

        //UNPACK OUR DATA FROM OUR BUNDLE
        int employeeId = this.getArguments().getInt("id");

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int monthInt = spinner.getSelectedItemPosition();
                System.out.println("Int month: " + monthInt);
                new GetDetailAsyncClass(getContext(), txtHours, employeeId).execute(monthInt);
            }
        });

        return layout;
    }


    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        if(view != null) {
            txtHours.setText(allHoursString);
        }
    }


    public class GetDetailAsyncClass extends AsyncTask<Integer, Void, Integer> {

        int employeeId;
        Context context;
        TextView txtView;

        public GetDetailAsyncClass(Context context, TextView txtView, int employeeId) {
            this.context = context;
            this.txtView = txtView;
            this.employeeId = employeeId;
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            Integer monthInt = integers[0];
            int allHours=0;
            int hours=0;

            employeeDatabase = EmployeeDatabase.getInstance(getActivity());

            // PROVJERA
            Log.d("GetTimeFragment id", String.valueOf(employeeId));
            Log.d("Month in getTimeAsync", String.valueOf(monthInt));

            List<Detail> employeeWithDetails = employeeDatabase.employeeDao().getEmployeesWithDetails(employeeId);

            for (Detail employeeWithDetail:employeeWithDetails) {


                Date date = employeeWithDetail.getDate();
                Date timeStart = employeeWithDetail.getTimeStart();
                Date timeEnd = employeeWithDetail.getTimeEnd();

                // PROVJERA
                Log.d("Month u bazi", String.valueOf(date.getMonth()));

                // parse timeStart
                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTime(timeStart);

                // parse timeEnd
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTime(timeEnd);

                if(monthInt == date.getMonth()) {
                    hours = (calendarEnd.get(Calendar.HOUR_OF_DAY) - calendarStart.get(Calendar.HOUR_OF_DAY));
                    Log.d("Hours", String.valueOf(hours));
                }

                allHours+=hours;
                Log.d("AllHours", String.valueOf(allHours));
            }

            allHoursString = String.valueOf(allHours);
            txtView.setText(allHoursString);

            return allHours;
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("extra", allHoursString);
    }
}