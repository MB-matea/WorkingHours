package com.mateabeslic.workinghours;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;

@Entity(tableName = "employee")
public class Employee {

    @PrimaryKey(autoGenerate = true)
    private Integer _id;

    private String name;

    public static Employee[] employees = {
            new Employee("Mate Matic"),
            new Employee("Ivo Ivic"),
            new Employee("Ante Antic")
    };

    public Employee(String name) {
        this.name = name;
    }

    public Employee() {}

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
