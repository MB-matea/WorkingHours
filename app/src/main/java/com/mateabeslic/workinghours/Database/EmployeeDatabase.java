package com.mateabeslic.workinghours.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mateabeslic.workinghours.Converters;
import com.mateabeslic.workinghours.DAO.EmployeeDao;
import com.mateabeslic.workinghours.Detail;
import com.mateabeslic.workinghours.Employee;

import java.util.concurrent.Executors;

@Database(entities = {Employee.class, Detail.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class EmployeeDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "db_employees";
    private static EmployeeDatabase employeeDatabase;

    public static synchronized EmployeeDatabase getInstance(final Context context) {
        if(employeeDatabase == null) {
            employeeDatabase = Room.databaseBuilder(context.getApplicationContext(), EmployeeDatabase.class, DATABASE_NAME)
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull final SupportSQLiteDatabase db) {
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    String firstRow = String.format("INSERT INTO employee(\"name\") VALUES (\"%s\")",
                                            Employee.employees[0].getName());
                                    String secondRow = String.format("INSERT INTO employee(\"name\") VALUES (\"%s\")",
                                            Employee.employees[1].getName());
                                    String thirdRow = String.format("INSERT INTO employee(\"name\") VALUES (\"%s\")",
                                            Employee.employees[2].getName());

                                    db.execSQL(firstRow);
                                    db.execSQL(secondRow);
                                    db.execSQL(thirdRow);
                                }
                            });
                        }
                    })
                    .build();
        }
        return employeeDatabase;
    }

    public abstract EmployeeDao employeeDao();
}
