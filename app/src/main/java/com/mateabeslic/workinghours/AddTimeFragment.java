package com.mateabeslic.workinghours;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTimeFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTimeFragment extends Fragment implements View.OnClickListener {

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

        date.setInputType(InputType.TYPE_NULL);
        timeStart.setInputType(InputType.TYPE_NULL);
        timeEnd.setInputType(InputType.TYPE_NULL);


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
}
