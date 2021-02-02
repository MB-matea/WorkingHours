package com.mateabeslic.workinghours;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class EmployeeWithDetails {
    @Embedded
    public Employee employee;

    @Relation(
            parentColumn = "_id",
            entityColumn = "id_fkEmployee"
    )
    public List<Detail> details;
}
