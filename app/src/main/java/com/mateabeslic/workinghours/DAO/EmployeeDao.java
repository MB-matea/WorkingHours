package com.mateabeslic.workinghours.DAO;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.mateabeslic.workinghours.Detail;
import com.mateabeslic.workinghours.Employee;
import com.mateabeslic.workinghours.EmployeeWithDetails;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM employee")
    List<Employee> getEmployeeList();

    @Query("SELECT * FROM employee")
    Cursor getEmployeeListCursor();

    @Query("SELECT * FROM employee WHERE _id = :id")
    Cursor findByIdCursor(Integer id);

    @Query("SELECT * FROM employee WHERE _id = :id")
    Employee findById(Integer id);

//    @Transaction
//    @Query("SELECT * FROM employee WHERE _id = :id")
//    public List<EmployeeWithDetails> getEmployeesWithDetails(Integer id);

    @Transaction
    @Query("SELECT * FROM detail WHERE id_fkEmployee = :id")
    public List<Detail> getEmployeesWithDetails(Integer id);


//    @Query("SELECT * FROM user WHERE birthday BETWEEN :from AND :to")
//    public List<Detail> getDetailsFromEmployee(Date from, Date to);

    @Insert
    void insertEmployee(Employee employee);

    @Insert
    void insertAll(Employee... employees);

    @Insert
    void insertDetail(Detail detail);

    @Update
    void updateEmployee(Employee employee);

    @Delete
    void deleteEmployee(Employee employee);

//    @Insert
//    void insertTimes(List <Time> times);
}
