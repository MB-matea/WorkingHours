package com.mateabeslic.workinghours;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mateabeslic.workinghours.Database.EmployeeDatabase;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;


public class AddTimeFragment extends Fragment implements View.OnClickListener {

    private EmployeeDatabase employeeDatabase;

    public AddTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_add_time, container, false);

        EditText date = layout.findViewById(R.id.date);
        EditText timeStart = layout.findViewById(R.id.time_start);
        EditText timeEnd = layout.findViewById(R.id.time_end);
        Button btnSave = layout.findViewById(R.id.btn_save);

        date.setInputType(InputType.TYPE_NULL);
        timeStart.setInputType(InputType.TYPE_NULL);
        timeEnd.setInputType(InputType.TYPE_NULL);

        //UNPACK OUR DATA FROM OUR BUNDLE
        int employeeId = this.getArguments().getInt("id");

        employeeDatabase = EmployeeDatabase.getInstance(getActivity());


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(date);
            }
        });

        timeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(timeStart);
            }
        });

        timeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(timeEnd);
            }
        });
        
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String employeeDate = date.getText().toString();
                String employeeTimeStart = timeStart.getText().toString();
                String employeeTimeEnd = timeEnd.getText().toString();

                if(employeeDate.isEmpty()) {
                    Toast.makeText(getContext(), "Niste unijeli datum!", Toast.LENGTH_SHORT).show();
                }
                else if (employeeTimeStart.isEmpty()) {
                    Toast.makeText(getContext(), "Niste unijeli vrijeme dolaska!", Toast.LENGTH_SHORT).show();
                }
                else if(employeeTimeEnd.isEmpty()) {
                    Toast.makeText(getContext(), "Niste unijeli vrijeme odlaska!", Toast.LENGTH_SHORT).show();
                }
                else {
                    new InsertDetailAsyncClass(employeeId).execute(employeeDate, employeeTimeStart, employeeTimeEnd);
                    Toast.makeText(getContext(), "Usješno ste unijeli podatke!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return layout;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.date) {
            onClickDate((EditText)v);
        } else if(v.getId() == R.id.time_start) {
            onClickTime((EditText) v);
        } else if(v.getId() == R.id.time_end) {
            onClickTime((EditText) v);
        }
    }


    private void onClickDate(EditText date) {
        showDateDialog(date);
    }


    public void onClickTime(EditText time) {
        showTimeDialog(time);
    }


    private void showDateDialog(EditText date) {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

                date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(getActivity(), dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    private void showTimeDialog(EditText timeStart) {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                timeStart.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new TimePickerDialog( getActivity(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true).show();
    }


    public class InsertDetailAsyncClass extends AsyncTask<String, Void, Void> {

        int employeeId;

        public InsertDetailAsyncClass(int employeeId) {
            this.employeeId = employeeId;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String date = strings[0];
            String timeStart = strings[1];
            String timeEnd = strings[2];
            employeeDatabase = EmployeeDatabase.getInstance(getActivity());

            // parse date
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            try {
                calendar.setTime(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // parse timeStart
            Calendar calendar1 = Calendar.getInstance();
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
            try {
                calendar1.setTime(sdf1.parse(timeStart));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // parse timeEnd
            Calendar calendar2 = Calendar.getInstance();
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
            try {
                calendar2.setTime(sdf2.parse(timeEnd));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date dbDate = calendar.getTime();
            Date dbTimeStart = calendar1.getTime();
            Date dbTimeEnd = calendar2.getTime();

            Detail detail = new Detail();

            // set fk
            detail.setId_fkEmployee(employeeId);

            // set date
            detail.setDate(dbDate);

            // set timeStart
            detail.setTimeStart(dbTimeStart);

            // set timeEnd
            detail.setTimeEnd(dbTimeEnd);

            employeeDatabase.employeeDao().insertDetail(detail);

            Log.d("Date", String.valueOf(detail.getDate()));
            Log.d("TimeStart", String.valueOf(detail.getTimeStart()));
            Log.d("TimeEnd", String.valueOf(detail.getTimeEnd()));

            return null;
        }
    }
}
