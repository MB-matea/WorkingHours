package com.mateabeslic.workinghours;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mateabeslic.workinghours.Database.EmployeeDatabase;

import java.util.concurrent.Executors;

public class NewEmployeeActivity extends AppCompatActivity {

    private EmployeeDatabase employeeDatabase;
    EditText edtName;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_employee);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // botun za natrag
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        edtName = findViewById(R.id.name);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new InsertEmployeeAsyncTask(edtName.getText().toString());
                String name = edtName.getText().toString();
                new InsertEmployeeAsyncTask().execute(name);
                finish();
//                saveNewUser(name);
            }
        });

    }


    public class InsertEmployeeAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... string) {
            String name = string [0];
            employeeDatabase = EmployeeDatabase.getInstance(NewEmployeeActivity.this);

            Employee employee = new Employee();
            employee.setName(name);

            employeeDatabase.employeeDao().insertEmployee(employee);
            return null;
        }
    }

}