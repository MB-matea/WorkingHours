package com.mateabeslic.workinghours;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GetTimeFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetTimeFragment extends Fragment {

    private EmployeeDatabase employeeDatabase;

    public GetTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_get_time, container, false);

        Spinner spinner = layout.findViewById(R.id.spn_months);
        Button btnShow = layout.findViewById(R.id.btn_show);
        TextView txtHours = layout.findViewById(R.id.txt_hours);

        //UNPACK OUR DATA FROM OUR BUNDLE
        int employeeId = this.getArguments().getInt("id");

//        String month = spinner.getSelectedItem().toString();



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

            employeeDatabase = EmployeeDatabase.getInstance(getActivity());

            System.out.println("Id u getTime: " + employeeId);
            System.out.println("Month u getTimeAsync: " + monthInt);

            List<Detail> employeeWithDetails = employeeDatabase.employeeDao().getEmployeesWithDetails(employeeId);

            int allHours=0;
            int hours=0;



            for (Detail employeeWithDetail:employeeWithDetails) {


                Date date = employeeWithDetail.getDate();
                Date timeStart = employeeWithDetail.getTimeStart();
                Date timeEnd = employeeWithDetail.getTimeEnd();

                System.out.println("MONTH u bazi: " + date.getMonth());
//                System.out.println("HOUR u bazi: " + timeEnd.getHours());

                // parse timeStart
                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTime(timeStart);

                System.out.println("HOUR u bazi: " + calendarStart.get(Calendar.HOUR));

                // parse timeEnd
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTime(timeEnd);

                if(monthInt == date.getMonth()) {
//                    System.out.println("Isti su!");
                    hours = (calendarEnd.get(Calendar.HOUR_OF_DAY) - calendarStart.get(Calendar.HOUR_OF_DAY));
                    System.out.println("Hours: " + hours);
                }
                allHours+=hours;
                System.out.println("AllHours: " + allHours);
                String allHoursString = String.valueOf(allHours);
                txtView.setText(allHoursString);
            }

            return allHours;
        }

    }
}