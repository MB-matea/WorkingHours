package com.mateabeslic.workinghours;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mateabeslic.workinghours.Database.EmployeeDatabase;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private EmployeeDatabase employeeDatabase;
    private Cursor employeeCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        employeeDatabase = EmployeeDatabase.getInstance(MainActivity.this);

        new EmployeeListAsyncTask(this, false).execute();


//        final Handler handler = new Handler();
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                employeeDatabase = EmployeeDatabase.getInstance(MainActivity.this);
//                employeeCursor = employeeDatabase.employeeDao().getEmployeeListCursor();
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        onGetCursor(employeeCursor);
//                    }
//                });
//
//            }
//        });

    }

//    public void onGetCursor(Cursor employeeCursor) {
//        ListView listEmployees = findViewById(R.id.employees_list);
//        listEmployees.setAdapter(new SimpleCursorAdapter(
//                this,
//                android.R.layout.simple_list_item_1,
//                employeeCursor,
//                new String[]{"name"},
//                new int[]{android.R.id.text1},
//                0
//        ));
//
//        listEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent(MainActivity.this, EmployeeDetailsActivity.class);
//                    intent.putExtra(EmployeeDetailsActivity.EXTRA_EMPLOYEEID, (int) id);
//                    startActivity(intent);
//            }
//        });
//
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        employeeDatabase = EmployeeDatabase.getInstance(MainActivity.this);
        new EmployeeListAsyncTask(this, true).execute();
    }

    public class EmployeeListAsyncTask extends AsyncTask<Void, Void, Cursor> {
        Context context;
        boolean onRestart;

        public EmployeeListAsyncTask(Context context, boolean onRestart) {
            this.context = context;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return employeeDatabase.employeeDao().getEmployeeListCursor();
        }

        @Override
        protected void onPostExecute(Cursor employeeCursor) {
            ListView listEmployees = findViewById(R.id.employees_list);

            if(!onRestart){
                listEmployees.setAdapter(new SimpleCursorAdapter(
                        context,
                        android.R.layout.simple_list_item_1,
                        employeeCursor,
                        new String[]{"name"},
                        new int[]{android.R.id.text1},
                        0
                ));

                listEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, EmployeeDetailsActivity.class);
                        intent.putExtra(EmployeeDetailsActivity.EXTRA_EMPLOYEEID, (int) id);
                        startActivity(intent);
                    }
                });
            }else {
                SimpleCursorAdapter adapter = (SimpleCursorAdapter) listEmployees.getAdapter();
                adapter.changeCursor(employeeCursor);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_create_employee:
                Intent intent = new Intent(MainActivity.this, NewEmployeeActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        employeeCursor.close();
//        employeeDatabase.close();
//    }


}